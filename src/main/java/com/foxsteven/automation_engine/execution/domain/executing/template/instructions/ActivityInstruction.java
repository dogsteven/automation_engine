package com.foxsteven.automation_engine.execution.domain.executing.template.instructions;


import com.foxsteven.automation_engine.execution.domain.executing.template.instructions.activity.Activity;

public record ActivityInstruction(String id,
                                  Activity activity,
                                  String nextInstructionId) implements Instruction {
    @Override
    public void handle(InstructionHandler handler) {
        handler.handleActivityInstruction(this);
    }
}
