package com.foxsteven.automation_engine.execution.domain.instance.exceptions;

import lombok.Getter;

import java.util.UUID;

public class InvalidExecutionStateException extends RuntimeException {
    @Getter
    private final UUID instanceId;

    @Getter
    private final String detail;

    public InvalidExecutionStateException(UUID instanceId, String detail) {
        super(String.format("Invalid execution instance state \"%s\": detail = %s", instanceId, detail));
        this.instanceId = instanceId;
        this.detail = detail;
    }
}
