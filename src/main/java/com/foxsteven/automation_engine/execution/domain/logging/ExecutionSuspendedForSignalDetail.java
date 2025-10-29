package com.foxsteven.automation_engine.execution.domain.logging;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Builder
public class ExecutionSuspendedForSignalDetail implements LogEntryDetail {
    private String signalToken;

    private OffsetDateTime timeoutTimestamp;
}
