package com.foxsteven.automation_engine.execution.infrastructure.orchestration.quartz.jobs.executing;

import com.foxsteven.automation_engine.execution.application.executing.ExecutionCommandHandler;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ResumeExecutionFromDrippingJob implements Job {
    private final ExecutionCommandHandler executionCommandHandler;

    public ResumeExecutionFromDrippingJob(ExecutionCommandHandler executionCommandHandler) {
        this.executionCommandHandler = executionCommandHandler;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        final var dataMap = jobExecutionContext.getMergedJobDataMap();

        final var instanceId = UUID.fromString(dataMap.getString("instanceId"));

        executionCommandHandler.resumeExecutionFromDripping(instanceId);
    }
}
