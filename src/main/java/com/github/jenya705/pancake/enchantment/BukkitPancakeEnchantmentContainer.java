package com.github.jenya705.pancake.enchantment;

import com.github.jenya705.pancake.Pancake;
import com.github.jenya705.pancake.item.PancakeItemSource;
import com.github.jenya705.pancake.item.PancakeItemStack;
import com.github.jenya705.pancake.item.event.PancakeItemEvent;
import io.papermc.paper.enchantments.EnchantmentRarity;
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
public class BukkitPancakeEnchantmentContainer implements PancakeEnchantmentContainer<Void> {

    private static final Map<String, BukkitPancakeEnchantmentContainer> vanillaEnchantments = new HashMap<>();
    static {
        for (Enchantment enchantment: Enchantment.values()) {
            vanillaEnchantments.put(enchantment.getKey().getKey().toUpperCase(Locale.ROOT), new BukkitPancakeEnchantmentContainer(enchantment));
        }
    }

    private Enchantment bukkitEnchantment;
    private Map<Class<? extends PancakeItemEvent>, List<List<BiFunction<PancakeItemEvent, PancakeEnchantmentObject, Void>>>> handlers;

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
    public EnchantmentRarity getRarity() {
        return getBukkitEnchantment().getRarity();
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

    @Override
    public void invokeEvent(PancakeItemEvent event, PancakeItemSource source, PancakeEnchantmentObject enchantmentObject) {
        if (getHandlers() == null) return;
        List<List<BiFunction<PancakeItemEvent, PancakeEnchantmentObject, Void>>> sourceHandlers = getHandlers().getOrDefault(event.getClass(), null);
        if (sourceHandlers == null) return;
        List<BiFunction<PancakeItemEvent, PancakeEnchantmentObject, Void>> exactHandlers = sourceHandlers.get(source.ordinal());
        if (exactHandlers == null) return;
        exactHandlers.forEach(handler -> handler.apply(event, enchantmentObject));
    }

    @Override
    public void addHandler(Class<? extends PancakeItemEvent> eventClazz, PancakeItemSource source, BiFunction<PancakeItemEvent, PancakeEnchantmentObject, Void> handler) {
        if (getHandlers() == null) setHandlers(new HashMap<>());
        List<List<BiFunction<PancakeItemEvent, PancakeEnchantmentObject, Void>>> sourceHandlers = getHandlers().getOrDefault(eventClazz, null);
        if (sourceHandlers == null) {
            sourceHandlers = new ArrayList<>(PancakeItemSource.values().length);
            for (int i = 0; i < PancakeItemSource.values().length; ++i) sourceHandlers.add(null);
            getHandlers().put(eventClazz, sourceHandlers);
        }
        List<BiFunction<PancakeItemEvent, PancakeEnchantmentObject, Void>> exactHandlers = sourceHandlers.get(source.ordinal());
        if (exactHandlers == null) {
            exactHandlers = new ArrayList<>();
            sourceHandlers.set(source.ordinal(), exactHandlers);
        }
        exactHandlers.add(handler);
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
