package com.foxsteven.automation_engine.execution.domain.logging;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExecutionRestartedDetail implements LogEntryDetail {
    private String restartInstructionId;
}
