package com.github.jenya705.pancake.enchantment;

import io.papermc.paper.enchantments.EnchantmentRarity;
import org.bukkit.enchantments.EnchantmentTarget;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PancakeEnchantment {

    /**
     * @return Localized name of enchantment
     */
    String name();

    /**
     * @return Id of enchantment
     */
    String id();

    /**
     * @return Enchantment target
     */
    EnchantmentTarget target();

    /**
     * @return Enchantment rarity
     */
    EnchantmentRarity rarity() default EnchantmentRarity.COMMON;

    /**
     * @return Max level of enchantment
     */
    int maxLevel() default 1;

    /**
     * @return Start level of enchantment
     */
    int startLevel() default 1;

    /**
     * @return Enchantments conflicts
     */
    String[] conflicts() default {};

    /**
     * @return true if the enchantment can be found in trades
     */
    boolean tradeable() default true;

    /**
     * @return true if the enchantment can be found in a table or by loot tables
     */
    boolean discoverable() default true;

    /**
     * Checks if this enchantment is a treasure enchantment.
     * <br>
     * Treasure enchantments can only be received via looting, trading, or
     * fishing.
     *
     * @return true if the enchantment is a treasure enchantment
     */
    boolean treasure() default true;


}
