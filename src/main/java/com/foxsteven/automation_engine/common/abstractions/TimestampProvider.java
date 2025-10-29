package com.foxsteven.automation_engine.common.abstractions;

import java.time.OffsetDateTime;

public interface TimestampProvider {
    OffsetDateTime provideOffsetDateTimeNow();
}
