package com.github.jenya705.pancake.nms;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public interface PancakeNMS {

    /**
     * @param itemStack Item Stack
     * @return Result of method c of nms item
     */
    int itemCMethod(ItemStack itemStack);

    /**
     *
     * Method - getMinCost()
     *
     * @param level Level
     * @param enchantment enchantment
     * @return Result of method a(Integer) of nms enchantment
     */
    int enchantmentAMethodInteger(Enchantment enchantment, int level);

    /**
     *
     * Method - getMaxCost()
     *
     * @param level Level
     * @param enchantment enchantment
     * @return Result of method b(Integer) of nms enchantment
     */
    int enchantmentBMethodInteger(Enchantment enchantment, int level);

}
