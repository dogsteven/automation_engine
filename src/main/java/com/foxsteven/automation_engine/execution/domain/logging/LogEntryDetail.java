package com.foxsteven.automation_engine.execution.domain.logging;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "__TYPE__"
)
@JsonSubTypes({
        @JsonSubTypes.Type(
                name = "EXECUTION_STARTED",
                value = ExecutionStartedDetail.class
        ),
        @JsonSubTypes.Type(
                name = "EXECUTION_ADVANCED",
                value = ExecutionAdvancedDetail.class
        ),
        @JsonSubTypes.Type(
                name = "EXECUTION_FAILED",
                value = ExecutionFailedDetail.class
        ),
        @JsonSubTypes.Type(
                name = "EXECUTION_RESTARTED",
                value = ExecutionRestartedDetail.class
        ),
        @JsonSubTypes.Type(
                name = "EXECUTION_FORCE_COMPLETED",
                value = ExecutionForceCompletedDetail.class
        ),
        @JsonSubTypes.Type(
                name = "EXECUTION_COMPLETED",
                value = ExecutionCompletedDetail.class
        ),
        @JsonSubTypes.Type(
                name = "EXECUTION_SUSPENDED_FOR_ACTIVITY_COMPLETION",
                value = ExecutionSuspendedForActivityCompletionDetail.class
        ),
        @JsonSubTypes.Type(
                name = "EXECUTION_SUSPENDED_FOR_SIGNAL",
                value = ExecutionSuspendedForSignalDetail.class
        ),
        @JsonSubTypes.Type(
                name = "EXECUTION_RESUMED_ON_SINAL",
                value = ExecutionResumedOnSignalDetail.class
        ),
        @JsonSubTypes.Type(
                name = "EXECUTION_SUSPENDED_FOR_DRIPPING",
                value = ExecutionSuspendedForDrippingDetail.class
        ),
})
public interface LogEntryDetail {
}
