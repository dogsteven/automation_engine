package com.foxsteven.automation_engine.execution.domain.executor.context;

public interface ActivityHandlingContext extends ReadWriteExecutionContext {
    String activityToken();
}
