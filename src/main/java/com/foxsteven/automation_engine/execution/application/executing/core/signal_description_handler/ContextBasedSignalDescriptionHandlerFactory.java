package com.foxsteven.automation_engine.execution.application.executing.core.signal_description_handler;

import com.foxsteven.automation_engine.execution.domain.executing.abstractions.SignalDescriptionHandlerFactory;
import com.foxsteven.automation_engine.execution.domain.executing.context.ReadInstructionExecutionContext;
import com.foxsteven.automation_engine.execution.domain.template.instructions.wait_signal.SignalDescriptionHandler;
import org.springframework.stereotype.Component;

@Component
public class ContextBasedSignalDescriptionHandlerFactory implements SignalDescriptionHandlerFactory {
    @Override
    public SignalDescriptionHandler create(ReadInstructionExecutionContext context) {
        return new ContextBasedSignalDescriptionHandler(context);
    }
}
