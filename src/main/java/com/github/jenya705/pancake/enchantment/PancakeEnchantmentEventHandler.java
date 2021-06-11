package com.github.jenya705.pancake.enchantment;

import com.github.jenya705.pancake.item.PancakeItemSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PancakeEnchantmentEventHandler {

    /**
     * @return ID of listening enchantment
     */
    String id() default "";

    /**
     * @return Source of item
     */
    PancakeItemSource source() default PancakeItemSource.MAIN;

}
