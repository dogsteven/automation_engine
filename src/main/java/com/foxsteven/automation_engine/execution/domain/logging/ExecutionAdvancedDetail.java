package com.foxsteven.automation_engine.execution.domain.logging;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExecutionAdvancedDetail implements LogEntryDetail {
    private String fromInstructionId;

    private String toInstructionId;
}
