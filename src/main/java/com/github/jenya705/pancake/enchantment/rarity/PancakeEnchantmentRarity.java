package com.github.jenya705.pancake.enchantment.rarity;

import io.papermc.paper.enchantments.EnchantmentRarity;

/**
 * Pancake enchantment rarity
 */
public interface PancakeEnchantmentRarity {

    float COMMON_VALUE = 10f;
    float UNCOMMON_VALUE = 5f;
    float RARE_VALUE = 2f;
    float VERY_RARE_VALUE = 1f;

    PancakeEnchantmentRarity COMMON = new PancakeEnchantmentRarityWrapper(EnchantmentRarity.COMMON.getWeight());
    PancakeEnchantmentRarity UNCOMMON = new PancakeEnchantmentRarityWrapper(EnchantmentRarity.UNCOMMON.getWeight());
    PancakeEnchantmentRarity RARE = new PancakeEnchantmentRarityWrapper(EnchantmentRarity.RARE.getWeight());
    PancakeEnchantmentRarity VERY_RARE = new PancakeEnchantmentRarityWrapper(EnchantmentRarity.VERY_RARE.getWeight());

    static PancakeEnchantmentRarity of(EnchantmentRarity bukkit) {
        if (bukkit == EnchantmentRarity.COMMON) return COMMON;
        if (bukkit == EnchantmentRarity.UNCOMMON) return UNCOMMON;
        if (bukkit == EnchantmentRarity.RARE) return RARE;
        if (bukkit == EnchantmentRarity.VERY_RARE) return VERY_RARE;
        return null;
    }

    static EnchantmentRarity asBukkit(PancakeEnchantmentRarity rarity) {
        if (rarity.getWeight() == COMMON_VALUE) return EnchantmentRarity.COMMON;
        if (rarity.getWeight() == UNCOMMON_VALUE) return EnchantmentRarity.UNCOMMON;
        if (rarity.getWeight() == RARE_VALUE) return EnchantmentRarity.RARE;
        if (rarity.getWeight() == VERY_RARE_VALUE) return EnchantmentRarity.VERY_RARE;
        return null;
    }

    static PancakeEnchantmentRarity of(float weight) {
        return new PancakeEnchantmentRarityWrapper(weight);
    }

    /**
     *
     * Weight of rarity
     *
     * @return weight
     */
    float getWeight();

}
