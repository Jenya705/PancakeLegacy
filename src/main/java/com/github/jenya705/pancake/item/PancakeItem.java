package com.github.jenya705.pancake.item;

import org.bukkit.Material;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PancakeItem {

    /**
     *
     * Return localized name of item (e.g. Diamond ore)
     *
     * @return Localized name of Item
     */
    String name();

    /**
     *
     * Return id of item (e.g. minecraft:diamond_ore)
     *
     * @return id of Item (e.g. "minecraft:diamond_ore")
     */
    String id();

    /**
     *
     * Return material which used to pretend item
     *
     * @return Material which used to pretend item
     */
    Material material();

    /**
     *
     * Return additional enchantment cost
     *
     * @return additional enchantment cost
     */
    int additionalEnchantmentCost() default 0;

}
