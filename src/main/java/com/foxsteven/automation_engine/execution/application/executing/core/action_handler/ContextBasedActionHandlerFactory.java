package com.foxsteven.automation_engine.execution.application.executing.core.action_handler;

import com.foxsteven.automation_engine.execution.domain.executor.abstractions.ActionHandlerFactory;
import com.foxsteven.automation_engine.execution.domain.executor.context.ActionHandlingContext;
import com.foxsteven.automation_engine.execution.domain.template.instructions.action.ActionHandler;
import org.springframework.stereotype.Component;

@Component
public class ContextBasedActionHandlerFactory implements ActionHandlerFactory {
    @Override
    public ActionHandler create(ActionHandlingContext context) {
        return new ContextBasedActionHandler(context);
    }
}
