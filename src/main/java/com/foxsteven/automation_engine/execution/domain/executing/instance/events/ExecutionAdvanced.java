package com.foxsteven.automation_engine.execution.domain.executing.instance.events;

import lombok.Getter;

import java.util.UUID;

public class ExecutionAdvanced {
    @Getter
    private final UUID instanceId;

    @Getter
    private final String fromInstructionId;

    @Getter
    private final String toInstructionId;

    public ExecutionAdvanced(UUID instanceId,
                             String fromInstructionId,
                             String toInstructionId) {
        this.instanceId = instanceId;
        this.fromInstructionId = fromInstructionId;
        this.toInstructionId = toInstructionId;
    }
}
