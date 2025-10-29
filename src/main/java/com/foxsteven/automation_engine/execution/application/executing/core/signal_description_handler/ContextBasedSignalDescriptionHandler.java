package com.foxsteven.automation_engine.execution.application.executing.core.signal_description_handler;

import com.foxsteven.automation_engine.execution.domain.executing.context.SignalDescriptionHandlingContext;
import com.foxsteven.automation_engine.execution.domain.executing.template.instructions.wait_signal.SignalDescriptionHandler;

public class ContextBasedSignalDescriptionHandler implements SignalDescriptionHandler {
    private final SignalDescriptionHandlingContext context;

    public ContextBasedSignalDescriptionHandler(SignalDescriptionHandlingContext context) {
        this.context = context;
    }
}
