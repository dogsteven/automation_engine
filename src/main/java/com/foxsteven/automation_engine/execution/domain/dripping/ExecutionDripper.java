package com.foxsteven.automation_engine.execution.domain.dripping;

import com.foxsteven.automation_engine.execution.domain.dripping.exceptions.InvalidDripperStateException;
import com.foxsteven.automation_engine.execution.domain.executing.instance.exceptions.InvalidExecutionStateException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.UUID;
import java.util.stream.Stream;

@Entity
@Table(name = "execution_dripper")
public class ExecutionDripper {
    @Id
    @Column(name = "id")
    @Getter
    private UUID id;

    @Column(name = "number_of_slots_per_drip")
    @Getter
    private int numberOfSlotsPerDripping;

    @Column(name = "dripping_interval")
    @Getter
    private long drippingInterval;

    @Column(name = "batch_size")
    @Getter
    private int batchSize;

    @Column(name = "number_of_pending_slots")
    private int numberOfPendingSlots;

    @Column(name = "is_dripping")
    @Getter
    private boolean isDripping;

    @Column(name = "number_of_dripped_slots_in_the_current_dripping")
    private int numberOfDrippedSlotsInTheCurrentDripping;

    public ExecutionDripper() {}

    public ExecutionDripper(UUID id, int numberOfSlotsPerDripping, int drippingInterval, int batchSize) {
        this.id = id;
        this.numberOfSlotsPerDripping = numberOfSlotsPerDripping;
        this.drippingInterval = drippingInterval;
        this.batchSize = batchSize;
        this.numberOfPendingSlots = 0;
        this.isDripping = false;
        this.numberOfDrippedSlotsInTheCurrentDripping = 0;
    }

    public void addSlot() {
        ++numberOfPendingSlots;
    }

    public void startDripping() {
        if (isDripping) {
            throw new InvalidExecutionStateException(id, "The dripper is dripping");
        }

        if (numberOfPendingSlots == 0) {
            throw new InvalidDripperStateException(id, "There is no pending slot");
        }

        isDripping = true;
    }

    public int dripBatch() {
        if (!isDripping) {
            throw new InvalidExecutionStateException(id, "The dripper is not dripping");
        }

        final var count = calculateNumberOfAvailableSlotsForTheCurrentDripping();

        numberOfPendingSlots -= count;
        numberOfDrippedSlotsInTheCurrentDripping += count;

        if (numberOfDrippedSlotsInTheCurrentDripping == numberOfSlotsPerDripping || numberOfPendingSlots == 0) {
            isDripping = false;
            numberOfDrippedSlotsInTheCurrentDripping = 0;
        }

        return count;
    }

    private int calculateNumberOfAvailableSlotsForTheCurrentDripping() {
        return Stream.of(
                numberOfPendingSlots,
                numberOfSlotsPerDripping - numberOfDrippedSlotsInTheCurrentDripping,
                batchSize
        ).min(Integer::compare).get();
    }
}
