package com.github.jenya705.pancake.enchantment;

import com.github.jenya705.pancake.item.PancakeItemSource;

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
