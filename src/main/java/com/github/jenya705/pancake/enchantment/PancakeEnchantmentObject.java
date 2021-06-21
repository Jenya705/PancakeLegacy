package com.github.jenya705.pancake.enchantment;

import com.github.jenya705.pancake.enchantment.container.BukkitPancakeEnchantmentContainer;
import com.github.jenya705.pancake.enchantment.container.PancakeEnchantmentContainer;
import com.github.jenya705.pancake.item.PancakeItemStack;

/**
 * Pancake enchantment object
 */
public interface PancakeEnchantmentObject {

    /**
     *
     * Return container of enchantment
     *
     * @return {@link PancakeEnchantmentContainer} (maybe {@link BukkitPancakeEnchantmentContainer} of enchantment
     */
    PancakeEnchantmentContainer<?> getEnchantmentContainer();

    /**
     *
     * Return level of enchantment
     *
     * @return Level of enchantment
     */
    int getLevel();

    /**
     *
     * Return owner of enchantment
     *
     * @return {@link PancakeItemStack} (maybe {@link com.github.jenya705.pancake.item.BukkitPancakeItemStack}) item with enchantment
     */
    PancakeItemStack getItemStack();

}
