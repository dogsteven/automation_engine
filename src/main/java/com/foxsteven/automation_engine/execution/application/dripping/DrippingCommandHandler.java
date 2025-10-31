package com.foxsteven.automation_engine.execution.application.dripping;

import com.foxsteven.automation_engine.common.abstractions.TimestampProvider;
import com.foxsteven.automation_engine.execution.application.abstractions.DrippingCommandScheduler;
import com.foxsteven.automation_engine.execution.application.abstractions.ExecutionCommandScheduler;
import com.foxsteven.automation_engine.execution.application.abstractions.repositories.DrippingSlotRepository;
import com.foxsteven.automation_engine.execution.application.abstractions.repositories.ExecutionDripperRepository;
import com.foxsteven.automation_engine.execution.application.exceptions.ExecutionDripperNotFoundException;
import com.foxsteven.automation_engine.execution.domain.dripping.DrippingSlot;
import com.foxsteven.automation_engine.execution.domain.dripping.ExecutionDripper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.UUID;

@Component
public class DrippingCommandHandler {
    private final ExecutionDripperRepository dripperRepository;

    private final DrippingSlotRepository drippingSlotRepository;

    private final DrippingCommandScheduler drippingCommandScheduler;

    private final ExecutionCommandScheduler executionCommandScheduler;

    private final TimestampProvider timestampProvider;

    public DrippingCommandHandler(
            ExecutionDripperRepository dripperRepository,
            DrippingSlotRepository drippingSlotRepository,
            DrippingCommandScheduler drippingCommandScheduler,
            ExecutionCommandScheduler executionCommandScheduler,
            TimestampProvider timestampProvider) {
        this.dripperRepository = dripperRepository;
        this.drippingSlotRepository = drippingSlotRepository;
        this.drippingCommandScheduler = drippingCommandScheduler;
        this.executionCommandScheduler = executionCommandScheduler;
        this.timestampProvider = timestampProvider;
    }

    @Transactional
    public void createExecutionDripper(UUID id, int numberOfSlotsPerDripping, int drippingInterval, int batchSize) {
        final var dripper = new ExecutionDripper(id, numberOfSlotsPerDripping, drippingInterval, batchSize);

        dripperRepository.saveAndFlush(dripper);

        drippingCommandScheduler.scheduleStartDripping(id, Duration.ofSeconds(drippingInterval));
    }

    @Transactional
    public void startDripping(UUID dripperId) {
        final var dripper = dripperRepository.findById(dripperId).orElse(null);

        if (dripper == null) {
            throw new ExecutionDripperNotFoundException(dripperId);
        }

        dripper.startDripping();

        dripperRepository.saveAndFlush(dripper);

        drippingCommandScheduler.scheduleDripBatch(dripperId, Duration.ofSeconds(2));
    }

    @Transactional
    public void createDrippingSlot(UUID dripperId, UUID instanceId) {
        final var dripper = dripperRepository.findById(dripperId).orElse(null);

        if (dripper == null) {
            throw new ExecutionDripperNotFoundException(dripperId);
        }

        final var now = timestampProvider.provideOffsetDateTimeNow();
        final var drippingSlot = new DrippingSlot(UUID.randomUUID(), dripperId, instanceId, now);

        dripper.addSlot();

        drippingSlotRepository.save(drippingSlot);
    }

    @Transactional
    public void dripBatch(UUID dripperId) {
        final var dripper = dripperRepository.findById(dripperId).orElse(null);

        if (dripper == null) {
            throw new ExecutionDripperNotFoundException(dripperId);
        }

        final var slotCount = dripper.dripBatch();

        final var pageRequest = PageRequest.of(
                0, slotCount,
                Sort.by(Sort.Direction.ASC, "timestamp")
        );

        final var drippingSlots = drippingSlotRepository.findByDripperId(dripperId, pageRequest);

        for (final var drippingSlot: drippingSlots) {
            executionCommandScheduler.enqueueResumeExecutionFromDripping(drippingSlot.getInstanceId());
        }

        drippingSlotRepository.deleteAll(drippingSlots);

        if (dripper.isDripping()) {
            drippingCommandScheduler.scheduleDripBatch(dripperId, Duration.ofSeconds(2));
        }
    }
}
