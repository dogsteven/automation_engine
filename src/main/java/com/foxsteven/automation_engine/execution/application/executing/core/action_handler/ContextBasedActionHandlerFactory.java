package com.foxsteven.automation_engine.execution.application.executing.core.action_handler;

import com.foxsteven.automation_engine.execution.domain.executing.abstractions.ActionHandlerFactory;
import com.foxsteven.automation_engine.execution.domain.executing.context.ReadWriteInstructionExecutionContext;
import com.foxsteven.automation_engine.execution.domain.template.instructions.action.ActionHandler;
import org.springframework.stereotype.Component;

@Component
public class ContextBasedActionHandlerFactory implements ActionHandlerFactory {
    @Override
    public ActionHandler create(ReadWriteInstructionExecutionContext context) {
        return new ContextBasedActionHandler(context);
    }
}
