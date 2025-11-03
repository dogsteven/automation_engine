package com.foxsteven.automation_engine.execution.domain.instance.events;

import lombok.Getter;

import java.util.UUID;

public class ExecutionSuspendedForDripping {
    @Getter
    private final UUID instanceId;

    @Getter
    private final String instructionId;

    @Getter
    private final UUID dripperId;

    public ExecutionSuspendedForDripping(UUID instanceId, String instructionId, UUID dripperId) {
        this.instanceId = instanceId;
        this.instructionId = instructionId;
        this.dripperId = dripperId;
    }
}
