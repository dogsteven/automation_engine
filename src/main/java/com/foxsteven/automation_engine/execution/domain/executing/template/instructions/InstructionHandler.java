package com.foxsteven.automation_engine.execution.domain.executing.template.instructions;

public interface InstructionHandler {
    void handleActionInstruction(ActionInstruction instruction);

    void handleConditionalInstruction(ConditionalInstruction instruction);

    void handleGoToInstruction(GoToInstruction instruction);

    void handleActivityInstruction(ActivityInstruction instruction);

    void handleWaitForSignalInstruction(WaitForSignalInstruction instruction);

    void handleSplitInstruction(SplitInstruction instruction);

    void handleDripInstruction(DripInstruction instruction);
}
