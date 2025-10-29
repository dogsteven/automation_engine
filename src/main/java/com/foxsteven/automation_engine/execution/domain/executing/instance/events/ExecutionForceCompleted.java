package com.foxsteven.automation_engine.execution.domain.executing.instance.events;

import lombok.Getter;

import java.util.UUID;

public class ExecutionForceCompleted {
    @Getter
    private final UUID instanceId;

    @Getter
    private final String instructionId;

    @Getter
    private final String reason;

    public ExecutionForceCompleted(UUID instanceId, String instructionId, String reason) {
        this.instanceId = instanceId;
        this.instructionId = instructionId;
        this.reason = reason;
    }
}
