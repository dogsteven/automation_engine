package com.foxsteven.automation_engine.execution.domain.instance.events;

import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

public class ExecutionSuspendedForSignal {
    @Getter
    private final UUID instanceId;

    @Getter
    private final String signalToken;

    @Getter
    private final OffsetDateTime timeoutTimestamp;

    public ExecutionSuspendedForSignal(UUID instanceId, String signalToken, OffsetDateTime timeoutTimestamp) {
        this.instanceId = instanceId;
        this.signalToken = signalToken;
        this.timeoutTimestamp = timeoutTimestamp;
    }
}
