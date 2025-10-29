package com.foxsteven.automation_engine.execution.domain.executing.instance.events;

import lombok.Getter;

import java.util.UUID;

public class ExecutionFailed {
    @Getter
    private final UUID instanceId;

    @Getter
    private final String failedInstructionId;

    @Getter
    private final String reason;

    public ExecutionFailed(UUID instanceId, String failedInstructionId, String reason) {
        this.instanceId = instanceId;
        this.failedInstructionId = failedInstructionId;
        this.reason = reason;
    }
}
