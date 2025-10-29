package com.foxsteven.automation_engine.execution.domain.executing.instance;

public enum ExecutionState {
    RUNNING,
    SUSPENDED_FOR_ACTIVITY_COMPLETION, SUSPENDED_FOR_SIGNAL, SUSPENDED_FOR_DRIPPING,
    COMPLETED, FAILED
}
