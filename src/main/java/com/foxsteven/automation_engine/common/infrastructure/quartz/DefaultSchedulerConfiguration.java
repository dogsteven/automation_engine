package com.foxsteven.automation_engine.common.infrastructure.quartz;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DefaultSchedulerConfiguration {
    private final Scheduler defaultScheduler;

    public DefaultSchedulerConfiguration(
            @Qualifier("Default")
            Scheduler defaultScheduler) {
        this.defaultScheduler = defaultScheduler;
    }

    @PostConstruct
    public void startDefaultScheduler() {
        try {
            defaultScheduler.start();
        } catch (SchedulerException exception) {
            throw new RuntimeException(exception);
        }
    }

    @PreDestroy
    public void shutdownDefaultScheduler() {
        try {
            defaultScheduler.shutdown(true);
        } catch (SchedulerException exception) {
            throw new RuntimeException(exception);
        }
    }
}
