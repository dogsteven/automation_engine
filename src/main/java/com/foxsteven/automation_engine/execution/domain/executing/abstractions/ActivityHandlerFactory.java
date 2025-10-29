package com.foxsteven.automation_engine.execution.domain.executing.abstractions;

import com.foxsteven.automation_engine.execution.domain.executing.context.ActivityHandlingContext;
import com.foxsteven.automation_engine.execution.domain.executing.template.instructions.activity.ActivityHandler;

public interface ActivityHandlerFactory {
    ActivityHandler create(ActivityHandlingContext context);
}
