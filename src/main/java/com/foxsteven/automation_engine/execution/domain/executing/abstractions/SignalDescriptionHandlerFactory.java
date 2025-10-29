package com.foxsteven.automation_engine.execution.domain.executing.abstractions;

import com.foxsteven.automation_engine.execution.domain.executing.context.SignalDescriptionHandlingContext;
import com.foxsteven.automation_engine.execution.domain.executing.template.instructions.wait_signal.SignalDescriptionHandler;

public interface SignalDescriptionHandlerFactory {
    SignalDescriptionHandler create(SignalDescriptionHandlingContext context);
}
