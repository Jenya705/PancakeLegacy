package com.github.jenya705.pancake.enchantment;

import io.papermc.paper.enchantments.EnchantmentRarity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.enchantments.EnchantmentTarget;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * Builder of {@link PancakeEnchantment}
 */
@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public class PancakeEnchantmentBuilder {

    private String name;
    private String id;
    private EnchantmentTarget target = null;
    private float rarity = EnchantmentRarity.COMMON.getWeight();
    private int maxLevel = 1;
    private int startLevel = 1;
    private String[] conflicts = new String[0];
    private boolean treasure = true;
    private boolean tradeable = true;
    private boolean discoverable = true;

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

    public PancakeEnchantmentBuilder rarity(float rarity) {
        setRarity(rarity);
        return this;
    }

    public PancakeEnchantmentBuilder maxLevel(int maxLevel) {
        setMaxLevel(maxLevel);
        return this;
    }

    public PancakeEnchantmentBuilder conflict(String conflict) {
        if (getConflicts() == null) setConflicts(new String[1]);
        else setConflicts(Arrays.copyOf(getConflicts(), getConflicts().length+1));
        getConflicts()[getConflicts().length-1] = conflict;
        return this;
    }

    public PancakeEnchantmentBuilder conflicts(String[] conflicts) {
        setConflicts(conflicts);
        return this;
    }

    public PancakeEnchantmentBuilder tradeable(boolean tradeable) {
        setTradeable(tradeable);
        return this;
    }

    public PancakeEnchantmentBuilder treasure(boolean treasure) {
        setTreasure(treasure);
        return this;
    }

    public PancakeEnchantmentBuilder discoverable(boolean discoverable) {
        setDiscoverable(discoverable);
        return this;
    }

    public PancakeEnchantmentBuilder startLevel(int startLevel) {
        setStartLevel(startLevel);
        return this;
    }

    /**
     * @throws IllegalStateException if required fields did not set or null
     * @return built {@link PancakeEnchantment} annotation
     */
    public PancakeEnchantment build() {
        if (getName() == null || getId() == null || getTarget() == null) {
            throw new IllegalStateException("name, id or target are null");
        }
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
            public float rarity() {
                return getRarity();
            }

            @Override
            public int maxLevel() {
                return getMaxLevel();
            }

            @Override
            public String[] conflicts() {
                return getConflicts();
            }

            @Override
            public boolean tradeable() {
                return isTradeable();
            }

            @Override
            public boolean discoverable() {
                return isDiscoverable();
            }

            @Override
            public boolean treasure() {
                return isTreasure();
            }

            @Override
            public int startLevel() {
                return getStartLevel();
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return PancakeEnchantment.class;
            }
        };
    }

}
