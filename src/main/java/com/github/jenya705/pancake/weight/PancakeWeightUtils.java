package com.github.jenya705.pancake.weight;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@UtilityClass
public class PancakeWeightUtils {

    public static PancakeWeight getRandomWeight(List<? extends PancakeWeight> weights, float random) {
        float weightsCount = getWeightsCount(weights);
        float localRandom = random % weightsCount;
        for (PancakeWeight weight : weights) {
            localRandom -= weight.getWeight();
            if (localRandom <= 0) return weight;
        }
        return null;
    }

    public static float getWeightsCount(List<? extends PancakeWeight> weights) {
        return (float) weights.stream().mapToDouble(PancakeWeight::getWeight).sum();
    }

    public static PancakeWeight getRandomWeightByFilter(List<? extends PancakeWeight> weights, float random, Predicate<PancakeWeight> predicate) {
        return getRandomWeight(
                weights.stream().filter(predicate).collect(Collectors.toList()), random
        );
    }

}
