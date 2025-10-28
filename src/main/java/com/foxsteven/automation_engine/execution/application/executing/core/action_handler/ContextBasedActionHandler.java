package com.foxsteven.automation_engine.execution.application.executing.core.action_handler;

import com.foxsteven.automation_engine.execution.domain.executing.context.ReadWriteInstructionExecutionContext;
import com.foxsteven.automation_engine.execution.domain.template.instructions.action.ActionHandler;

public class ContextBasedActionHandler implements ActionHandler {
    private final ReadWriteInstructionExecutionContext context;

    public ContextBasedActionHandler(ReadWriteInstructionExecutionContext context) {
        this.context = context;
    }
}
