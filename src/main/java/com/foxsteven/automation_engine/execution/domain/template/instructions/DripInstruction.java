package com.foxsteven.automation_engine.execution.domain.template.instructions;

import java.util.UUID;

public record DripInstruction(String id,
                              UUID dripperId,
                              String nextInstructionId) implements Instruction {
    @Override
    public void handle(InstructionHandler handler) {
        handler.handleDripInstruction(this);
    }
}
