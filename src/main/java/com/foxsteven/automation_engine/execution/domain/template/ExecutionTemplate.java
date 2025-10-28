package com.foxsteven.automation_engine.execution.domain.template;

import com.foxsteven.automation_engine.execution.domain.template.instructions.Instruction;
import com.foxsteven.automation_engine.execution.infrastructure.persistence.converters.InstructionListAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "execution_template")
public class ExecutionTemplate {
    @Id
    @Column(name = "id")
    @Getter
    private UUID id;

    @Column(name = "start_instruction_id")
    @Getter
    private String startInstructionId;

    @Column(name = "instructions", columnDefinition = "text")
    @Convert(converter = InstructionListAttributeConverter.class)
    @Getter
    private List<Instruction> instructions;

    public ExecutionTemplate() {}

    public ExecutionTemplate(UUID id) {
        this.id = id;
    }

    public Instruction findInstructionById(String instructionId) {
        if (instructionId == null || instructions == null) {
            return null;
        }

        return instructions.stream()
                .filter(instruction -> instructionId.equals(instruction.id()))
                .findFirst()
                .orElse(null);
    }

    public void setInstructions(String startInstructionId, List<Instruction> instructions) {
        this.startInstructionId = startInstructionId;
        this.instructions = instructions;
    }
}
