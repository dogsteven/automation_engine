package com.foxsteven.automation_engine.execution.domain.executing.context;

import java.util.Map;

public interface ReadWriteExecutionContext extends ReadExecutionContext {
    void writeVariable(String name, Object value);

    void writeVariables(Map<String, Object> nameValueMap);

    void removeVariable(String name);
}
