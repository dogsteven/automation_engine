package com.foxsteven.automation_engine.execution.application.exceptions;

import lombok.Getter;

import java.util.UUID;

public class ExecutionInstanceNotFoundException extends RuntimeException {
    @Getter
    private final UUID instanceId;

    public ExecutionInstanceNotFoundException(UUID instanceId) {
        super(String.format("Execution instance with id \"%s\" is not found", instanceId));
        this.instanceId = instanceId;
    }
}
