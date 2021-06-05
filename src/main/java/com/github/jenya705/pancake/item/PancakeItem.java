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
     * @return Localized name of Item
     */
    String name();

    /**
     * @return String id of Item, example: "minecraft:diamond_ore"
     */
    String id();

    /**
     * @return Material which used to pretend Item
     */
    Material material();

}
