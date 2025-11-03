package com.foxsteven.automation_engine.execution.domain.template.instructions.activity;

public record SleepActivity(Long sleepDuration) implements Activity {
    @Override
    public void handle(ActivityHandler handler) {
        handler.handleSleepActivity(this);
    }
}
