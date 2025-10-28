package com.foxsteven.automation_engine.execution.domain.logging;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExecutionForceCompletedDetail implements LogEntryDetail {
    private String instructionId;

    private String reason;
}
