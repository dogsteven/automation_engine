package com.foxsteven.automation_engine.execution.application.executing.core.activity_handler;

import com.foxsteven.automation_engine.execution.domain.executing.context.ActivityHandlingContext;
import com.foxsteven.automation_engine.execution.domain.executing.template.instructions.activity.ActivityHandler;
import com.foxsteven.automation_engine.execution.domain.executing.template.instructions.activity.SleepActivity;

public class ContextBasedActivityHandler implements ActivityHandler {
    private final ActivityHandlingContext context;

    private final SleepActivityHandler sleepActivityHandler;

    public ContextBasedActivityHandler(
            ActivityHandlingContext context,
            SleepActivityHandler sleepActivityHandler) {
        this.context = context;
        this.sleepActivityHandler = sleepActivityHandler;
    }

    @Override
    public void handleSleepActivity(SleepActivity activity) {
        sleepActivityHandler.handle(activity, context);
    }
}
