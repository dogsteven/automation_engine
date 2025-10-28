package com.foxsteven.automation_engine.execution.domain.executing.abstractions;

import com.foxsteven.automation_engine.execution.domain.executing.context.ReadWriteInstructionExecutionContext;
import com.foxsteven.automation_engine.execution.domain.template.instructions.activity.ActivityHandler;

public interface ActivityHandlerFactory {
    ActivityHandler create(ReadWriteInstructionExecutionContext context);
}
