package com.foxsteven.automation_engine.execution.infrastructure.orchestration;

import com.foxsteven.automation_engine.execution.application.abstractions.DrippingCommandScheduler;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class QuartzDrippingCommandScheduler implements DrippingCommandScheduler {
    @Override
    public void enqueueDripSomeSlots(UUID dripperId) {

    }
}
