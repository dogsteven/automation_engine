package com.foxsteven.automation_engine.execution.application.abstractions;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface ExecutionCommandScheduler {
    void enqueueExecuteCurrentInstruction(UUID instanceId);

    void enqueueResumeExecutionFromDripping(UUID instanceId);

    void scheduleTimeoutExecutionSignalWaiting(UUID instanceId, String signalToken, OffsetDateTime timeoutTimestamp);

    void unscheduleTimeoutExecutionSignalWaiting(UUID instanceId, String signalToken);
}
