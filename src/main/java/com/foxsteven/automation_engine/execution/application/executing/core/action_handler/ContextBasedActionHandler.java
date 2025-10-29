package com.foxsteven.automation_engine.execution.application.executing.core.action_handler;

import com.foxsteven.automation_engine.execution.domain.executing.context.ActionHandlingContext;
import com.foxsteven.automation_engine.execution.domain.executing.template.instructions.action.ActionHandler;

public class ContextBasedActionHandler implements ActionHandler {
    private final ActionHandlingContext context;

    public ContextBasedActionHandler(ActionHandlingContext context) {
        this.context = context;
    }
}
