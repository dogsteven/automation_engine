package com.foxsteven.automation_engine.execution.domain.executor.abstractions;

import com.foxsteven.automation_engine.execution.domain.executor.context.ActivityHandlingContext;
import com.foxsteven.automation_engine.execution.domain.template.instructions.activity.ActivityHandler;

public interface ActivityHandlerFactory {
    ActivityHandler create(ActivityHandlingContext context);
}
