package com.github.jenya705.pancake.enchantment;

/**
 * Interface to count enchantment costs
 */
public interface PancakeEnchantmentCost {

    /**
     *
     * Return minimal cost of this enchantment depends on level
     *
     * @param level Level
     * @return Minimal cost
     */
    int getMinCost(int level);

    /**
     *
     * Return maximal cost of this enchantment depends on level
     *
     * @param level Level
     * @return maximal cost
     */
    int getMaxCost(int level);

}
