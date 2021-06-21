package com.github.jenya705.pancake.enchantment;

import com.github.jenya705.pancake.item.PancakeItemSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enchantment event handler annotation, which says to pancake if this method is event handler
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PancakeEnchantmentEventHandler {

    /**
     *
     * Return id of listening enchantment or empty if default
     *
     * @return ID of listening enchantment or empty if default
     */
    String id() default "";

    /**
     *
     * Return source of item when handler will be invoked
     *
     * @return Source of item
     */
    PancakeItemSource source() default PancakeItemSource.MAIN;

}
