package com.github.jenya705.pancake.enchantment;

import com.github.jenya705.pancake.item.PancakeItemStack;

public interface PancakeEnchantmentObject {

    /**
     * @return {@link PancakeEnchantmentContainer} (maybe {@link BukkitPancakeEnchantmentContainer} of enchantment
     */
    PancakeEnchantmentContainer<?> getEnchantmentContainer();

    /**
     * @return Level of enchantment
     */
    int getLevel();

    /**
     * @return {@link PancakeItemStack} (maybe {@link com.github.jenya705.pancake.item.BukkitPancakeItemStack}) item with enchantment
     */
    PancakeItemStack getItemStack();

}
