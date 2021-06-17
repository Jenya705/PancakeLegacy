package com.github.jenya705.pancake.enchantment;

import com.github.jenya705.pancake.item.PancakeItemSource;
import com.github.jenya705.pancake.item.event.PancakeItemEvent;
import io.papermc.paper.enchantments.EnchantmentRarity;
import org.bukkit.enchantments.EnchantmentTarget;

import java.util.function.BiFunction;

public interface PancakeEnchantmentContainer<T> {

    String getName();

    String getId();

    EnchantmentTarget getTarget();

    EnchantmentRarity getRarity();

    /**
     * @param enchantmentID Enchantment id
     * @return is that enchantment is conflicts with this enchantment
     */
    boolean isConflict(String enchantmentID);

    boolean isTreasure();

    boolean isTradeable();

    boolean isDiscoverable();

    T getSource();

    int getMaxLevel();

    void invokeEvent(PancakeItemEvent event, PancakeItemSource source, PancakeEnchantmentObject enchantmentObject);

    void addHandler(Class<? extends PancakeItemEvent> eventClazz, PancakeItemSource source, BiFunction<PancakeItemEvent, PancakeEnchantmentObject, Void> handler);

}
