package com.foxsteven.automation_engine.execution.domain.instance.events;

import lombok.Getter;

import java.util.UUID;

public class ExecutionSuspendedForActivityCompletion {
    @Getter
    private final UUID instanceId;

    @Getter
    private final String instructionId;

    public ExecutionSuspendedForActivityCompletion(UUID instanceId, String instructionId) {
        this.instanceId = instanceId;
        this.instructionId = instructionId;
    }
}
