package com.foxsteven.automation_engine.execution.domain.instance.events;

import lombok.Getter;

import java.util.UUID;

public class ExecutionCompleted {
    @Getter
    private final UUID instanceId;

    @Getter
    private final String lastInstructionId;

    public ExecutionCompleted(UUID instanceId, String lastInstructionId) {
        this.instanceId = instanceId;
        this.lastInstructionId = lastInstructionId;
    }
}
