package com.foxsteven.automation_engine.execution.domain.executing.instance.events;

import lombok.Getter;

import java.util.UUID;

public class ExecutionStarted {
    @Getter
    private final UUID instanceId;

    @Getter
    private final String startInstructionId;

    public ExecutionStarted(UUID instanceId, String startInstructionId) {
        this.instanceId = instanceId;
        this.startInstructionId = startInstructionId;
    }
}
