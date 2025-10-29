package com.foxsteven.automation_engine.execution.domain.executing.instance.events;

import lombok.Getter;

import java.util.UUID;

public class ExecutionResumedOnSignal {
    @Getter
    private final UUID instanceId;

    @Getter
    private final String signalToken;

    public ExecutionResumedOnSignal(UUID instanceId, String signalToken) {
        this.instanceId = instanceId;
        this.signalToken = signalToken;
    }
}
