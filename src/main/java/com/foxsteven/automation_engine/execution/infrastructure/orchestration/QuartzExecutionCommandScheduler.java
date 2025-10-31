package com.foxsteven.automation_engine.execution.infrastructure.orchestration;

import com.foxsteven.automation_engine.common.infrastructure.quartz.topic.TopicBasedSchedulerProvider;
import com.foxsteven.automation_engine.execution.application.abstractions.ExecutionCommandScheduler;
import com.foxsteven.automation_engine.execution.infrastructure.orchestration.quartz.jobs.executing.ExecuteCurrentInstructionJob;
import com.foxsteven.automation_engine.execution.infrastructure.orchestration.quartz.jobs.executing.ResumeExecutionFromDrippingJob;
import org.quartz.*;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

@Component
public class QuartzExecutionCommandScheduler implements ExecutionCommandScheduler {
    private final TopicBasedSchedulerProvider schedulerProvider;

    public QuartzExecutionCommandScheduler(TopicBasedSchedulerProvider schedulerProvider) {
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
    public void enqueueExecuteCurrentInstruction(UUID instanceId) {
        final var scheduler = schedulerProvider.provide("executing");

        final var jobDetail = JobBuilder.newJob(ExecuteCurrentInstructionJob.class)
                .withIdentity("ExecuteCurrentInstruction", "executing")
                .storeDurably(true)
                .build();

        final var trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(instanceId.toString(), "executing")
                .usingJobData("instanceId", instanceId.toString())
                .startNow()
                .build();

        try {
            schedule(scheduler, jobDetail, trigger);
        } catch (SchedulerException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void enqueueResumeExecutionFromDripping(UUID instanceId) {
        final var scheduler = schedulerProvider.provide("executing");

        final var jobDetail = JobBuilder.newJob(ResumeExecutionFromDrippingJob.class)
                .withIdentity("ResumeExecutionFromDripping", "executing")
                .storeDurably(true)
                .build();

        final var trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(instanceId.toString(), "executing")
                .usingJobData("instanceId", instanceId.toString())
                .startNow()
                .build();

        try {
            schedule(scheduler, jobDetail, trigger);
        } catch (SchedulerException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void scheduleTimeoutExecutionSignalWaiting(UUID instanceId,
                                                      String signalToken,
                                                      OffsetDateTime timeoutTimestamp) {
        final var scheduler = schedulerProvider.provide(instanceId, "executing");

        final var jobDetail = JobBuilder.newJob(ExecuteCurrentInstructionJob.class)
                .withIdentity("ExecuteCurrentInstruction", "executing")
                .storeDurably(true)
                .build();

        final var trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(instanceId.toString() + ":" + signalToken, "executing:timeout")
                .usingJobData("instanceId", instanceId.toString())
                .usingJobData("signalToken", signalToken)
                .startAt(Date.from(timeoutTimestamp.toInstant()))
                .build();

        try {
            schedule(scheduler, jobDetail, trigger);
        } catch (SchedulerException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void unscheduleTimeoutExecutionSignalWaiting(UUID instanceId, String signalToken) {
        final var scheduler = schedulerProvider.provide(instanceId, "executing");

        final var triggerKey = TriggerKey
                .triggerKey(instanceId.toString() + ":" + signalToken, "executing");

        try {
            if (scheduler.checkExists(triggerKey)) {
                scheduler.unscheduleJob(triggerKey);
            }
        } catch (SchedulerException exception) {
            throw new RuntimeException(exception);
        }
    }
}
