package com.foxsteven.automation_engine.execution.domain.instance;

public enum ExecutionState {
    RUNNING,
    WAITING_FOR_ACTIVITY_COMPLETION, WAITING_FOR_SIGNAL,
    SUSPENDED_FOR_DRIPPING,
    COMPLETED, FAILED
}
