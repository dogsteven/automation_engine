package com.foxsteven.automation_engine.execution.domain.template.instructions;

public record GoToInstruction(String id,
                              String nextInstructionId) implements Instruction {
    @Override
    public void handle(InstructionHandler handler) {
        handler.handleGoToInstruction(this);
    }
}
