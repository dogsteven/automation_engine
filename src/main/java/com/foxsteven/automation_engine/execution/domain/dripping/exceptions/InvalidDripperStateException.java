package com.foxsteven.automation_engine.execution.domain.dripping.exceptions;

import lombok.Getter;

import java.util.UUID;

public class InvalidDripperStateException extends RuntimeException {
    @Getter
    private final UUID dripperId;

    @Getter
    private final String detail;

    public InvalidDripperStateException(UUID dripperId, String detail) {
        super(String.format("Invalid execution dripper state \"%s\": detail = %s", dripperId, detail));
        this.dripperId = dripperId;
        this.detail = detail;
    }
}
