package com.foxsteven.automation_engine.execution.application.abstractions;

import java.time.Duration;
import java.util.UUID;

public interface DrippingCommandScheduler {
    void scheduleStartDripping(UUID dripperId, Duration interval);

    void scheduleDripBatch(UUID dripperId, Duration afterDuration);
}
