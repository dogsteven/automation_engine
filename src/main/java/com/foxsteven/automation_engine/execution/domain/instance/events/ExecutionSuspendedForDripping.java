package com.foxsteven.automation_engine.execution.domain.instance.events;

import lombok.Getter;

import java.util.UUID;

public class ExecutionSuspendedForDripping {
    @Getter
    private final UUID instanceId;

    @Getter
    private final UUID templateId;

    @Getter
    private final String instructionId;

    public ExecutionSuspendedForDripping(UUID instanceId, UUID templateId, String instructionId) {
        this.instanceId = instanceId;
        this.templateId = templateId;
        this.instructionId = instructionId;
    }
}
