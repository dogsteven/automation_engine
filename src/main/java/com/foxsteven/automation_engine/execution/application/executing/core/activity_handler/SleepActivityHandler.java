package com.foxsteven.automation_engine.execution.application.executing.core.activity_handler;

import com.foxsteven.automation_engine.execution.domain.executing.context.ReadWriteInstructionExecutionContext;
import com.foxsteven.automation_engine.execution.domain.template.instructions.activity.SleepActivity;
import org.springframework.stereotype.Component;

@Component
public class SleepActivityHandler {
    public void handle(SleepActivity activity, ReadWriteInstructionExecutionContext context) {
    }
}
