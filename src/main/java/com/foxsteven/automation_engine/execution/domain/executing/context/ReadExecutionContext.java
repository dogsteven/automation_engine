package com.foxsteven.automation_engine.execution.domain.executing.context;

import java.util.UUID;

public interface ReadExecutionContext {
    UUID instanceId();

    boolean checkVariableExists(String name);

    Object readVariable(String name);
}
