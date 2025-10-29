package com.foxsteven.automation_engine.execution.application.executing.core.activity_handler;

import com.foxsteven.automation_engine.execution.domain.executing.abstractions.ActivityHandlerFactory;
import com.foxsteven.automation_engine.execution.domain.executing.context.ActivityHandlingContext;
import com.foxsteven.automation_engine.execution.domain.executing.template.instructions.activity.ActivityHandler;
import org.springframework.stereotype.Component;

@Component
public class ContextBasedActivityHandlerFactory implements ActivityHandlerFactory {
    private final SleepActivityHandler sleepActivityHandler;

    public ContextBasedActivityHandlerFactory(
            SleepActivityHandler sleepActivityHandler) {
        this.sleepActivityHandler = sleepActivityHandler;
    }

    @Override
    public ActivityHandler create(ActivityHandlingContext context) {
        return new ContextBasedActivityHandler(
                context,
                sleepActivityHandler);
    }
}
