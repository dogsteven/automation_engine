package com.foxsteven.automation_engine.execution.infrastructure.persistence.converters;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxsteven.automation_engine.execution.domain.logging.LogEntryDetail;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LogEntryDetailAttributeConverter implements AttributeConverter<LogEntryDetail, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(LogEntryDetail detail) {
        if (detail == null) {
            return null;
        }

        try {
            return objectMapper.writeValueAsString(detail);
        } catch (Exception exception) {
            throw new RuntimeException("Failed to serialize log entry detail", exception);
        }
    }

    @Override
    public LogEntryDetail convertToEntityAttribute(String string) {
        try {
            return objectMapper.readValue(string, new TypeReference<>() {});
        } catch (Exception exception) {
            throw new RuntimeException("Failed to deserialize log entry detail", exception);
        }
    }
}
