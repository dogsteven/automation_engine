package com.foxsteven.automation_engine.execution.domain.executing.abstractions;

import com.foxsteven.automation_engine.execution.domain.executing.context.ActionHandlingContext;
import com.foxsteven.automation_engine.execution.domain.executing.template.instructions.action.ActionHandler;

public interface ActionHandlerFactory {
    ActionHandler create(ActionHandlingContext context);
}
