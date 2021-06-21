package com.github.jenya705.pancake.nms;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 *
 * Interface to interact with nms side of minecraft server
 *
 */
public interface PancakeNMS {

    /**
     *
     * Return result of method c() of nms item
     *
     * @param itemStack Item Stack
     * @return Result of method c of nms item
     */
    int itemCMethod(ItemStack itemStack);

    /**
     *
     * Return result of method a(Integer) of nms enchantment
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
     * Return result of method b(Integer) of nms enchantment
     *
     * Method - getMaxCost()
     *
     * @param level Level
     * @param enchantment enchantment
     * @return Result of method b(Integer) of nms enchantment
     */
    int enchantmentBMethodInteger(Enchantment enchantment, int level);

}
