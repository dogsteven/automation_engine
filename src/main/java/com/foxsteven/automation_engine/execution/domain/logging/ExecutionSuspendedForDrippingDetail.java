package com.foxsteven.automation_engine.execution.domain.logging;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ExecutionSuspendedForDrippingDetail implements LogEntryDetail {
    private String instructionId;

    private UUID dripperId;
}
