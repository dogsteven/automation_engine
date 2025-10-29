package com.foxsteven.automation_engine.execution.domain.executing.template.instructions;

import com.foxsteven.automation_engine.execution.domain.executing.template.instructions.conditional.ConditionBranch;

import java.util.List;

public record ConditionalInstruction(String id,
                                     List<ConditionBranch> branches,
                                     String fallbackInstructionId) implements Instruction {
    @Override
    public void handle(InstructionHandler handler) {
        handler.handleConditionalInstruction(this);
    }
}
