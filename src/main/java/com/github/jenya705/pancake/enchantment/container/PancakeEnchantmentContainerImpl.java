package com.github.jenya705.pancake.enchantment.container;

import com.github.jenya705.pancake.enchantment.PancakeEnchantment;
import com.github.jenya705.pancake.enchantment.PancakeEnchantmentCost;
import com.github.jenya705.pancake.enchantment.PancakeEnchantmentObject;
import com.github.jenya705.pancake.enchantment.PancakeEnchantmentWrapper;
import com.github.jenya705.pancake.enchantment.rarity.PancakeEnchantmentRarity;
import com.github.jenya705.pancake.item.PancakeItemSource;
import com.github.jenya705.pancake.item.PancakeItemStack;
import com.github.jenya705.pancake.item.event.PancakeItemEvent;
import com.github.jenya705.pancake.util.PancakeUtils;
import lombok.*;
import org.bukkit.enchantments.EnchantmentTarget;

import java.util.*;
import java.util.function.BiFunction;

@Getter
@Setter(AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class PancakeEnchantmentContainerImpl<T> extends EventablePancakeEnchantmentContainer<T> {

    private String name;
    private String id;
    private EnchantmentTarget target;
    private PancakeEnchantmentRarity rarity;
    private int maxLevel;
    private int startLevel;
    private String[] conflicts;
    private boolean treasure;
    private boolean tradeable;
    private boolean discoverable;
    private T source;
    private PancakeEnchantmentWrapper wrapper;

    /**
     * @param source Source object
     * @throws IllegalArgumentException if source does not have {@link PancakeEnchantment} annotation
     */
    public PancakeEnchantmentContainerImpl(T source) {
        Class<?> clazz = source.getClass();
        PancakeEnchantment pancakeEnchantment = clazz.getAnnotation(PancakeEnchantment.class);
        if (pancakeEnchantment == null) throw new IllegalArgumentException("Source does not have PancakeEnchantment annotation");
        setValues(source, pancakeEnchantment);
    }

    /**
     * @param source Source object
     * @param pancakeEnchantment PancakeEnchantment annotation
     */
    public PancakeEnchantmentContainerImpl(T source, PancakeEnchantment pancakeEnchantment) {
        setValues(source, pancakeEnchantment);
    }

    protected void setValues(T source, PancakeEnchantment pancakeEnchantment) {
        setName(pancakeEnchantment.name());
        setId(pancakeEnchantment.id());
        setTarget(pancakeEnchantment.target());
        setRarity(PancakeEnchantmentRarity.of(pancakeEnchantment.rarity()));
        setMaxLevel(pancakeEnchantment.maxLevel());
        setStartLevel(pancakeEnchantment.startLevel());
        setConflicts(pancakeEnchantment.conflicts());
        setTreasure(pancakeEnchantment.treasure());
        setTradeable(pancakeEnchantment.tradeable());
        setDiscoverable(pancakeEnchantment.discoverable());
        setSource(source);
        setWrapper(new PancakeEnchantmentWrapper(this));
    }

    @Override
    public boolean isConflict(String enchantmentID) {
        return PancakeUtils.contains(getConflicts(), enchantmentID);
    }

    @Override
    public boolean canApply(PancakeItemStack itemStack) {
        return getTarget().includes(itemStack.getBukkit());
    }

    @Override
    public int getMinCost(int level) {
        return getSource() instanceof PancakeEnchantmentCost ? ((PancakeEnchantmentCost) getSource()).getMinCost(level) : 1 + level * 10;
    }

    @Override
    public int getMaxCost(int level) {
        return getSource() instanceof PancakeEnchantmentCost ? ((PancakeEnchantmentCost) getSource()).getMaxCost(level) : getMinCost(level) + 5;
    }

    @Override
    public PancakeEnchantmentWrapper getWrapper() {
        if (wrapper == null) setWrapper(new PancakeEnchantmentWrapper(this));
        return wrapper;
    }

    public static class PancakeEnchantmentContainerImplBuilder<T> {

        public PancakeEnchantmentContainerImplBuilder() {
            conflicts = new String[0];
            discoverable = true;
            tradeable = true;
            treasure = true;
            maxLevel = 1;
            startLevel = 1;
            rarity = PancakeEnchantmentRarity.COMMON;
        }

        public PancakeEnchantmentContainerImplBuilder<T> conflict(String conflict) {
            if (conflicts == null) {
                conflicts = new String[1];
                conflicts[0] = conflict;
            }
            else {
                conflicts = Arrays.copyOf(conflicts, conflicts.length + 1);
                conflicts[conflicts.length - 1] = conflict;
            }
            return this;
        }

    }

}
