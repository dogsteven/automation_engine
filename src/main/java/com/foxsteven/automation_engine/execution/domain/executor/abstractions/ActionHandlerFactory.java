package com.foxsteven.automation_engine.execution.domain.executor.abstractions;

import com.foxsteven.automation_engine.execution.domain.executor.context.ActionHandlingContext;
import com.foxsteven.automation_engine.execution.domain.template.instructions.action.ActionHandler;

public interface ActionHandlerFactory {
    ActionHandler create(ActionHandlingContext context);
}
