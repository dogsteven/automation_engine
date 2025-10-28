package com.foxsteven.automation_engine.execution.application.executing.core.signal_description_handler;

import com.foxsteven.automation_engine.execution.domain.executing.context.ReadInstructionExecutionContext;
import com.foxsteven.automation_engine.execution.domain.template.instructions.wait_signal.SignalDescriptionHandler;

public class ContextBasedSignalDescriptionHandler implements SignalDescriptionHandler {
    private final ReadInstructionExecutionContext context;

    public ContextBasedSignalDescriptionHandler(ReadInstructionExecutionContext context) {
        this.context = context;
    }
}
