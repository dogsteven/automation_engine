package com.foxsteven.automation_engine.execution.domain.executing.template.instructions;

import com.foxsteven.automation_engine.execution.domain.executing.template.instructions.wait_signal.SignalDescription;

public record WaitForSignalInstruction(String id,
                                       SignalDescription signalDescription,
                                       String nextInstructionId,
                                       Long timeoutDuration,
                                       String nextInstructionIdOnTimeout) implements Instruction {
    @Override
    public void handle(InstructionHandler handler) {
        handler.handleWaitForSignalInstruction(this);
    }
}
