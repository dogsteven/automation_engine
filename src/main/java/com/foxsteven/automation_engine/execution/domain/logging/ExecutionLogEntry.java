package com.foxsteven.automation_engine.execution.domain.logging;

import com.foxsteven.automation_engine.execution.infrastructure.persistence.converters.LogEntryDetailAttributeConverter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "execution_log_entry")
@IdClass(ExecutionLogEntry.Identifier.class)
public class ExecutionLogEntry {
    @Id
    @Column(name = "instance_id")
    @Getter
    private UUID instanceId;

    @Id
    @Column(name = "log_entry_id")
    @Getter
    private UUID logEntryId;

    @Column(name = "detail", columnDefinition = "text")
    @Convert(converter = LogEntryDetailAttributeConverter.class)
    @Getter
    private LogEntryDetail detail;

    @Column(name = "timestamp")
    @Getter
    private OffsetDateTime timestamp;

    public ExecutionLogEntry() {}

    public ExecutionLogEntry(UUID instanceId, UUID logEntryId, LogEntryDetail detail, OffsetDateTime timestamp) {
        this.instanceId = instanceId;
        this.logEntryId = logEntryId;
        this.detail = detail;
        this.timestamp = timestamp;
    }

    @Builder
    @Getter
    public static class Identifier implements Serializable {
        private UUID instanceId;

        private UUID logEntryId;

        public Identifier() {}
    }
}
