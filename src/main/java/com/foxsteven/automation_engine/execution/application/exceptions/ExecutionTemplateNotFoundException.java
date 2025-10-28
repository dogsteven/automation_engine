package com.foxsteven.automation_engine.execution.application.exceptions;

import lombok.Getter;

import java.util.UUID;

public class ExecutionTemplateNotFoundException extends RuntimeException {
    @Getter
    private final UUID templateId;

    public ExecutionTemplateNotFoundException(UUID templateId) {
        super(String.format("Execution template with id \"%s\" is not found", templateId));
        this.templateId = templateId;
    }
}
