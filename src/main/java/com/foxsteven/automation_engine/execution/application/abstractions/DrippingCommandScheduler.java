package com.foxsteven.automation_engine.execution.application.abstractions;

import java.util.UUID;

public interface DrippingCommandScheduler {
    void enqueueDripSomeSlots(UUID dripperId);
}
