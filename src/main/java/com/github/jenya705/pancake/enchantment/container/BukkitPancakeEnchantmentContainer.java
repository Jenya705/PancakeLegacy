package com.github.jenya705.pancake.enchantment.container;

import com.github.jenya705.pancake.Pancake;
import com.github.jenya705.pancake.enchantment.PancakeEnchantmentObject;
import com.github.jenya705.pancake.enchantment.rarity.PancakeEnchantmentRarity;
import com.github.jenya705.pancake.item.PancakeItemSource;
import com.github.jenya705.pancake.item.PancakeItemStack;
import com.github.jenya705.pancake.item.event.PancakeItemEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;

import java.util.*;
import java.util.function.BiFunction;

/**
 *
 * Imitate bukkit enchantment as Pancake enchantment
 *
 */
@Setter(AccessLevel.PROTECTED)
@Getter
public class BukkitPancakeEnchantmentContainer extends EventablePancakeEnchantmentContainer<Void> {

    private static final Map<String, BukkitPancakeEnchantmentContainer> vanillaEnchantments = new HashMap<>();
    static {
        for (Enchantment enchantment: Enchantment.values()) {
            vanillaEnchantments.put(enchantment.getKey().getKey().toUpperCase(Locale.ROOT), new BukkitPancakeEnchantmentContainer(enchantment));
        }
    }

    private Enchantment bukkitEnchantment;

    public BukkitPancakeEnchantmentContainer(Enchantment bukkitEnchantment) {
        setBukkitEnchantment(bukkitEnchantment);
    }

    @Override
    public String getName() {
        return getBukkitEnchantment().getKey().getKey();
    }

    @Override
    public String getId() {
        return getBukkitEnchantment().getKey().getNamespace() + ":" + getName();
    }

    @Override
    public EnchantmentTarget getTarget() {
        return getBukkitEnchantment().getItemTarget();
    }

    @Override
    public PancakeEnchantmentRarity getRarity() {
        return PancakeEnchantmentRarity.of(getBukkitEnchantment().getRarity());
    }

    @Override
    public boolean isConflict(String enchantmentID) {
        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.fromString(enchantmentID));
        if (enchantment == null) return false;
        return getBukkitEnchantment().conflictsWith(enchantment);
    }

    @Override
    public boolean isTreasure() {
        return getBukkitEnchantment().isTreasure();
    }

    @Override
    public boolean isTradeable() {
        return getBukkitEnchantment().isTradeable();
    }

    @Override
    public boolean isDiscoverable() {
        return getBukkitEnchantment().isDiscoverable();
    }

    @Override
    public Void getSource() {
        return null;
    }

    @Override
    public int getMaxLevel() {
        return getBukkitEnchantment().getMaxLevel();
    }

    @Override
    public boolean canApply(PancakeItemStack itemStack) {
        return getTarget().includes(itemStack.getBukkit());
    }

    @Override
    public int getStartLevel() {
        return getBukkitEnchantment().getStartLevel();
    }

    @Override
    public int getMinCost(int level) {
        return Pancake.getPlugin().getNms().enchantmentAMethodInteger(getBukkitEnchantment(), level);
    }

    @Override
    public int getMaxCost(int level) {
        return Pancake.getPlugin().getNms().enchantmentBMethodInteger(getBukkitEnchantment(), level);
    }

    /**
     * @param enchantment Enchantment
     * @return {@link BukkitPancakeEnchantmentContainer} instance
     */
    public static BukkitPancakeEnchantmentContainer getVanillaEnchantment(Enchantment enchantment) {
        return getVanillaEnchantment(enchantment.getKey().getKey());
    }

    /**
     * @param key Enchantment name
     * @return {@link BukkitPancakeEnchantmentContainer} instance
     */
    public static BukkitPancakeEnchantmentContainer getVanillaEnchantment(String key) {
        return vanillaEnchantments.getOrDefault(key.toUpperCase(Locale.ROOT), null);
    }

    /**
     * @return Values of vanilla enchantments
     */
    public static BukkitPancakeEnchantmentContainer[] values() {
        return vanillaEnchantments.values().toArray(new BukkitPancakeEnchantmentContainer[0]);
    }

    @Override
    public Enchantment getWrapper() {
        return getBukkitEnchantment();
    }
}
