package com.foxsteven.automation_engine.execution.domain.executing.abstractions;

import com.foxsteven.automation_engine.execution.domain.executing.context.ReadInstructionExecutionContext;
import com.foxsteven.automation_engine.execution.domain.template.instructions.wait_signal.SignalDescriptionHandler;

public interface SignalDescriptionHandlerFactory {
    SignalDescriptionHandler create(ReadInstructionExecutionContext context);
}
