package com.foxsteven.automation_engine.common.infrastructure;

import com.foxsteven.automation_engine.common.abstractions.TimestampProvider;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class SystemTimestampProvider implements TimestampProvider {
    @Override
    public OffsetDateTime provideOffsetDateTimeNow() {
        return OffsetDateTime.now();
    }
}
