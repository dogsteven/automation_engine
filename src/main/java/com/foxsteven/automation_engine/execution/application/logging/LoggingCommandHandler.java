package com.foxsteven.automation_engine.execution.application.logging;

import com.foxsteven.automation_engine.common.abstractions.TimestampProvider;
import com.foxsteven.automation_engine.execution.application.abstractions.repositories.ExecutionLogEntryRepository;
import com.foxsteven.automation_engine.execution.domain.logging.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Component
public class LoggingCommandHandler {
    private final ExecutionLogEntryRepository executionLogEntryRepository;

    private final TimestampProvider timestampProvider;

    public LoggingCommandHandler(
            ExecutionLogEntryRepository executionLogEntryRepository,
            TimestampProvider timestampProvider) {
        this.executionLogEntryRepository = executionLogEntryRepository;
        this.timestampProvider = timestampProvider;
    }

    @Transactional
    public void createLogEntryForExecutionStarted(UUID instanceId, String startInstructionId) {
        final var detail = ExecutionStartedDetail.builder()
                .startInstructionId(startInstructionId)
                .build();

        final var now = timestampProvider.provideOffsetDateTimeNow();

        final var logEntry = new ExecutionLogEntry(instanceId, UUID.randomUUID(), detail, now);

        executionLogEntryRepository.save(logEntry);
    }

    @Transactional
    public void createLogEntryForExecutionAdvanced(UUID instanceId, String fromInstructionId, String toInstructionId) {
        final var detail = ExecutionAdvancedDetail.builder()
                .fromInstructionId(fromInstructionId)
                .toInstructionId(toInstructionId)
                .build();

        final var now = timestampProvider.provideOffsetDateTimeNow();

        final var logEntry = new ExecutionLogEntry(instanceId, UUID.randomUUID(), detail, now);

        executionLogEntryRepository.save(logEntry);
    }

    @Transactional
    public void createLogEntryForExecutionFailed(UUID instanceId, String failedInstructionId, String reason) {
        final var detail = ExecutionFailedDetail.builder()
                .failedInstructionId(failedInstructionId)
                .reason(reason)
                .build();

        final var now = timestampProvider.provideOffsetDateTimeNow();

        final var logEntry = new ExecutionLogEntry(instanceId, UUID.randomUUID(), detail, now);

        executionLogEntryRepository.save(logEntry);
    }

    @Transactional
    public void createLogEntryForExecutionRestarted(UUID instanceId, String restartInstructionId) {
        final var detail = ExecutionRestartedDetail.builder()
                .restartInstructionId(restartInstructionId)
                .build();

        final var now = timestampProvider.provideOffsetDateTimeNow();

        final var logEntry = new ExecutionLogEntry(instanceId, UUID.randomUUID(), detail, now);

        executionLogEntryRepository.save(logEntry);
    }

    @Transactional
    public void createLogEntryForExecutionCompleted(UUID instanceId, String lastInstructionId) {
        final var detail = ExecutionCompletedDetail.builder()
                .lastInstructionId(lastInstructionId)
                .build();

        final var now = timestampProvider.provideOffsetDateTimeNow();

        final var logEntry = new ExecutionLogEntry(instanceId, UUID.randomUUID(), detail, now);

        executionLogEntryRepository.save(logEntry);
    }

    @Transactional
    public void createLogEntryForExecutionForceCompleted(UUID instanceId, String instructionId, String reason) {
        final var detail = ExecutionForceCompletedDetail.builder()
                .instructionId(instructionId)
                .reason(reason)
                .build();

        final var now = timestampProvider.provideOffsetDateTimeNow();

        final var logEntry = new ExecutionLogEntry(instanceId, UUID.randomUUID(), detail, now);

        executionLogEntryRepository.save(logEntry);
    }

    @Transactional
    public void createLogEntryForExecutionSuspendedForActivityCompletion(UUID instanceId, String instructionId) {
        final var detail = ExecutionSuspendedForActivityCompletionDetail.builder()
                .instructionId(instructionId)
                .build();

        final var now = timestampProvider.provideOffsetDateTimeNow();

        final var logEntry = new ExecutionLogEntry(instanceId, UUID.randomUUID(), detail, now);

        executionLogEntryRepository.save(logEntry);
    }

    @Transactional
    public void createLogEntryForExecutionSuspendedForSignal(UUID instanceId,
                                                             String instructionId,
                                                             OffsetDateTime timeoutTimestamp) {
        final var detail = ExecutionSuspendedForSignalDetail.builder()
                .signalToken(instructionId)
                .timeoutTimestamp(timeoutTimestamp)
                .build();

        final var now = timestampProvider.provideOffsetDateTimeNow();

        final var logEntry = new ExecutionLogEntry(instanceId, UUID.randomUUID(), detail, now);

        executionLogEntryRepository.save(logEntry);
    }

    @Transactional
    public void createLogEntryForExecutionSuspendedForDripping(UUID instanceId, String instructionId, UUID dripperId) {
        final var detail = ExecutionSuspendedForDrippingDetail.builder()
                .instructionId(instructionId)
                .dripperId(dripperId)
                .build();

        final var now = timestampProvider.provideOffsetDateTimeNow();

        final var logEntry = new ExecutionLogEntry(instanceId, UUID.randomUUID(), detail, now);

        executionLogEntryRepository.save(logEntry);
    }
}
