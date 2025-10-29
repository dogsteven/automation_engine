package com.foxsteven.automation_engine.execution.domain.executing.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class DiscreteDistributionSampler {
    private static final ThreadLocal<Random> random = new ThreadLocal<>();

    public static <T> T sample(List<T> elements, Function<T, Float> weightAccessor) {
        if (elements == null || elements.isEmpty()) {
            return null;
        }

        if (elements.size() == 1) {
            return elements.get(0);
        }

        final var accumulativeDensities = new ArrayList<Float>(elements.size() + 1);
        accumulativeDensities.add(0f);

        var density = 0f;

        for (final var element: elements) {
            density += weightAccessor.apply(element);
            accumulativeDensities.add(density);
        }

        for (var index = 0; index < accumulativeDensities.size(); ++index) {
            accumulativeDensities.set(index, accumulativeDensities.get(index) * 100f / density);
        }

        if (random.get() == null) {
            random.set(new Random());
        }

        final var normalizedRandom = random.get().nextFloat() * 100f;

        if (normalizedRandom <= accumulativeDensities.get(0)) {
            return elements.get(0);
        }

        var left = 0;
        var right = elements.size();

        // induction hypothesis:
        // accumulativeDensities.get(right) >= normalizedRandom > accumulativeDensities.get(left)
        while (left < right - 1) {
            final var middle = (left + right) / 2;

            if (accumulativeDensities.get(middle) < normalizedRandom) {
                left = middle;
            } else {
                right = middle;
            }
        }

        return elements.get(left);
    }
}
