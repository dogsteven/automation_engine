package com.foxsteven.automation_engine.execution.domain.logging;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExecutionFailedDetail implements LogEntryDetail {
    private String failedInstructionId;

    private String reason;
}
