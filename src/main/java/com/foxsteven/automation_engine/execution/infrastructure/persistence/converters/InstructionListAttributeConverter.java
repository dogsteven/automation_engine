package com.foxsteven.automation_engine.execution.infrastructure.persistence.converters;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxsteven.automation_engine.execution.domain.template.instructions.Instruction;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter(autoApply = true)
public class InstructionListAttributeConverter implements AttributeConverter<List<Instruction>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Instruction> instructions) {
        if (instructions == null) {
            return null;
        }

        try {
            return objectMapper.writeValueAsString(instructions);
        } catch (Exception exception) {
            throw new RuntimeException("Failed to serialize instructions", exception);
        }
    }

    @Override
    public List<Instruction> convertToEntityAttribute(String string) {
        try {
            return objectMapper.readValue(string, new TypeReference<>() {});
        } catch (Exception exception) {
            throw new RuntimeException("Failed to deserialize instructions", exception);
        }
    }
}
