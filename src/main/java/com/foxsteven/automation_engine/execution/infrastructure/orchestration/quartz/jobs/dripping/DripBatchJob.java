package com.foxsteven.automation_engine.execution.infrastructure.orchestration.quartz.jobs.dripping;

import com.foxsteven.automation_engine.execution.application.dripping.DrippingCommandHandler;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DripBatchJob implements Job {
    private final DrippingCommandHandler drippingCommandHandler;

    public DripBatchJob(DrippingCommandHandler drippingCommandHandler) {
        this.drippingCommandHandler = drippingCommandHandler;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        final var dataMap = jobExecutionContext.getMergedJobDataMap();

        final var dripperId = UUID.fromString(dataMap.getString("dripperId"));

        drippingCommandHandler.dripBatch(dripperId);
    }
}
