package com.foxsteven.automation_engine.execution.domain.template;

import com.foxsteven.automation_engine.execution.domain.template.instructions.*;
import com.foxsteven.automation_engine.execution.domain.template.instructions.conditional.ConditionBranch;
import com.foxsteven.automation_engine.execution.domain.template.instructions.split_branch.SplitBranch;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

@Component
public class TemplateValidator {
    public String validateAndGetStartInstructionId(List<Instruction> instructions) {
        final var adjacency = buildAdjacency(instructions);

        final var startInstructionId = extractStartInstructionId(adjacency);

        detectCycle(startInstructionId, adjacency);

        return startInstructionId;
    }

    public Map<String, List<String>> buildAdjacency(List<Instruction> instructions) {
        final var adjacency = new HashMap<String, List<String>>();

        final var extractor = new NextInstructionIdExtractor();

        for (final var instruction: instructions) {
            final var nextInstructionIds = extractor.extract(instruction);

            if (adjacency.containsKey(instruction.id())) {
                throw new RuntimeException("Each instruction must have a unique id");
            }

            adjacency.put(instruction.id(), nextInstructionIds);
        }

        return adjacency;
    }

    public String extractStartInstructionId(Map<String, List<String>> adjacency) {
        final var candidateStartInstructionIds = new HashSet<>(adjacency.keySet());

        for (final var entry: adjacency.entrySet()) {
            for (final var nextInstructionId: entry.getValue()) {
                candidateStartInstructionIds.remove(nextInstructionId);
            }
        }

        if (candidateStartInstructionIds.size() != 1) {
            throw new RuntimeException("There must be only one start instruction");
        }

        return candidateStartInstructionIds.stream()
                .findFirst()
                .get();
    }

    public void detectCycle(String startInstructionId, Map<String, List<String>> adjacency) {
        final var visited = new HashSet<String>();
        final var cycleDetected = detectCycle(startInstructionId, adjacency, visited, new HashSet<>());

        if (cycleDetected) {
            throw new RuntimeException("Cycles are not allowed");
        }

        if (visited.size() != adjacency.size()) {
            throw new RuntimeException("Some instructions are not reachable");
        }
    }

    public boolean detectCycle(String instructionId,
                               Map<String, List<String>> adjacency,
                               Set<String> visited,
                               Set<String> path) {
        visited.add(instructionId);
        path.add(instructionId);

        for (final var nextInstruction: adjacency.get(instructionId)) {
            if (!visited.contains(nextInstruction)) {
                if (detectCycle(nextInstruction, adjacency, visited, path)) {
                    return true;
                }
            } else if (path.contains(nextInstruction)) {
                return true;
            }
        }

        path.remove(instructionId);
        return false;
    }

    public static class NextInstructionIdExtractor implements InstructionHandler {
        private Stream<String> nextInstructionIds;

        public List<String> extract(Instruction instruction) {
            instruction.handle(this);

            return nextInstructionIds
                    .filter(Objects::nonNull)
                    .toList();
        }

        @Override
        public void handleActionInstruction(ActionInstruction instruction) {
            nextInstructionIds = Stream.of(instruction.nextInstructionId());
        }

        @Override
        public void handleConditionalInstruction(ConditionalInstruction instruction) {
            nextInstructionIds = Stream.concat(
                    instruction.branches()
                            .stream()
                            .map(ConditionBranch::nextInstructionId),
                    Stream.of(instruction.fallbackInstructionId())
            );
        }

        @Override
        public void handleGoToInstruction(GoToInstruction instruction) {
            nextInstructionIds = Stream.of(instruction.nextInstructionId());
        }

        @Override
        public void handleActivityInstruction(ActivityInstruction instruction) {
            nextInstructionIds = Stream.of(instruction.nextInstructionId());
        }

        @Override
        public void handleWaitForSignalInstruction(WaitForSignalInstruction instruction) {
            nextInstructionIds = Stream.of(instruction.nextInstructionId(), instruction.nextInstructionIdOnTimeout());
        }

        @Override
        public void handleSplitInstruction(SplitInstruction instruction) {
            nextInstructionIds = instruction.branches()
                    .stream()
                    .map(SplitBranch::nextInstructionId);
        }

        @Override
        public void handleDripInstruction(DripInstruction instruction) {
            nextInstructionIds = Stream.of(instruction.nextInstructionId());
        }
    }
}
