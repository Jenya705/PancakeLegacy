package com.github.jenya705.pancake.enchantment;

import io.papermc.paper.enchantments.EnchantmentRarity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.enchantments.EnchantmentTarget;

import java.lang.annotation.Annotation;

/**
 * Builder of {@link PancakeEnchantment}
 */
@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public class PancakeEnchantmentBuilder {

    private String name;
    private String id;
    private EnchantmentTarget target;
    private EnchantmentRarity rarity = EnchantmentRarity.COMMON;
    private int maxLevel = 1;

    public static PancakeEnchantmentBuilder builder() {
        return new PancakeEnchantmentBuilder();
    }

    public PancakeEnchantmentBuilder name(String name) {
        setName(name);
        return this;
    }

    public PancakeEnchantmentBuilder id(String id) {
        setId(id);
        return this;
    }

    public PancakeEnchantmentBuilder target(EnchantmentTarget target) {
        setTarget(target);
        return this;
    }

    public PancakeEnchantmentBuilder rarity(EnchantmentRarity rarity) {
        setRarity(rarity);
        return this;
    }

    public PancakeEnchantmentBuilder maxLevel(int maxLevel) {
        setMaxLevel(maxLevel);
        return this;
    }

    public PancakeEnchantment build() {
        return new PancakeEnchantment(){
            @Override
            public String name() {
                return getName();
            }

            @Override
            public String id() {
                return getId();
            }

            @Override
            public EnchantmentTarget target() {
                return getTarget();
            }

            @Override
            public EnchantmentRarity rarity() {
                return getRarity();
            }

            @Override
            public int maxLevel() {
                return getMaxLevel();
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return PancakeEnchantment.class;
            }
        };
    }

}
