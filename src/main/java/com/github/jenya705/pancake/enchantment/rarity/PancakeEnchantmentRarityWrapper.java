package com.github.jenya705.pancake.enchantment.rarity;

public record PancakeEnchantmentRarityWrapper(float weight) implements PancakeEnchantmentRarity {
    @Override
    public float getWeight() {
        return weight();
    }
}
