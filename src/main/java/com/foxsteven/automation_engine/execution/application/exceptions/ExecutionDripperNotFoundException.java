package com.foxsteven.automation_engine.execution.application.exceptions;

import lombok.Getter;

import java.util.UUID;

public class ExecutionDripperNotFoundException extends RuntimeException {
    @Getter
    private final UUID dripperId;

    public ExecutionDripperNotFoundException(UUID dripperId) {
        super(String.format("Execution dripper with id \"%s\" is not found", dripperId));
        this.dripperId = dripperId;
    }
}
