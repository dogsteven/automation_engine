package com.foxsteven.automation_engine.execution.domain.executing.template.instructions;

import com.foxsteven.automation_engine.execution.domain.executing.template.instructions.action.Action;

public record ActionInstruction(String id,
                                Action action,
                                String nextInstructionId) implements Instruction {
    @Override
    public void handle(InstructionHandler handler) {
        handler.handleActionInstruction(this);
    }
}
