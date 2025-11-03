package com.foxsteven.automation_engine.execution.domain.executor.abstractions;

import com.foxsteven.automation_engine.execution.domain.executor.context.SignalDescriptionHandlingContext;
import com.foxsteven.automation_engine.execution.domain.template.instructions.wait_signal.SignalDescriptionHandler;

public interface SignalDescriptionHandlerFactory {
    SignalDescriptionHandler create(SignalDescriptionHandlingContext context);
}
