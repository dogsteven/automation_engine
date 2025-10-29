package com.foxsteven.automation_engine.execution.domain.executing.template.instructions;


import com.foxsteven.automation_engine.execution.domain.executing.template.instructions.split_branch.SplitBranch;

import java.util.List;

public record SplitInstruction(String id,
                               List<SplitBranch> branches) implements Instruction {
    @Override
    public void handle(InstructionHandler handler) {
        handler.handleSplitInstruction(this);
    }
}
