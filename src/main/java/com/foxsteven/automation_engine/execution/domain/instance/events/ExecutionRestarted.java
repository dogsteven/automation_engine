package com.foxsteven.automation_engine.execution.domain.instance.events;

import lombok.Getter;

import java.util.UUID;

public class ExecutionRestarted {
    @Getter
    private final UUID instanceId;

    @Getter
    private final String restartInstructionId;

    public ExecutionRestarted(UUID instanceId, String restartInstructionId) {
        this.instanceId = instanceId;
        this.restartInstructionId = restartInstructionId;
    }
}
