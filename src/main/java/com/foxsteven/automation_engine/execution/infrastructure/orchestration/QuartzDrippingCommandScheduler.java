package com.foxsteven.automation_engine.execution.infrastructure.orchestration;

import com.foxsteven.automation_engine.common.infrastructure.quartz.topic.TopicBasedSchedulerProvider;
import com.foxsteven.automation_engine.execution.application.abstractions.DrippingCommandScheduler;
import com.foxsteven.automation_engine.execution.infrastructure.orchestration.quartz.jobs.dripping.DripBatchJob;
import com.foxsteven.automation_engine.execution.infrastructure.orchestration.quartz.jobs.dripping.StartDrippingJob;
import org.quartz.*;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class QuartzDrippingCommandScheduler implements DrippingCommandScheduler {
    private final TopicBasedSchedulerProvider schedulerProvider;

    public QuartzDrippingCommandScheduler(TopicBasedSchedulerProvider schedulerProvider) {
        this.schedulerProvider = schedulerProvider;
    }

    private static void schedule(Scheduler scheduler, JobDetail jobDetail, Trigger trigger)
            throws SchedulerException {
        if (!scheduler.checkExists(jobDetail.getKey())) {
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            scheduler.scheduleJob(trigger);
        }
    }

    @Override
    public void scheduleStartDripping(UUID dripperId, Duration interval) {
        final var scheduler = schedulerProvider.provide("dripping");

        final var jobDetail = JobBuilder.newJob(StartDrippingJob.class)
                .withIdentity("StartDripping", "dripping")
                .storeDurably(true)
                .build();

        final var trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(dripperId.toString(), "dripping")
                .usingJobData("dripperId", dripperId.toString())
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .repeatForever()
                        .withIntervalInMinutes(interval.toMinutesPart()))
                .build();

        try {
            schedule(scheduler, jobDetail, trigger);
        } catch (SchedulerException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void scheduleDripBatch(UUID dripperId, Duration afterDuration) {
        final var scheduler = schedulerProvider.provide("dripping");

        final var jobDetail = JobBuilder.newJob(DripBatchJob.class)
                .withIdentity("DripBatch", "dripping")
                .storeDurably(true)
                .build();

        final var trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(dripperId.toString(), "dripping:dripBatch")
                .usingJobData("dripperId", dripperId.toString())
                .startAt(Date.from(Instant.now().plus(afterDuration)))
                .build();

        try {
            schedule(scheduler, jobDetail, trigger);
        } catch (SchedulerException exception) {
            throw new RuntimeException(exception);
        }
    }
}
