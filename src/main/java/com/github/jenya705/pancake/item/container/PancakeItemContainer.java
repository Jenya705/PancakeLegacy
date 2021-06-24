package com.github.jenya705.pancake.item.container;

import com.github.jenya705.pancake.item.PancakeItem;
import com.github.jenya705.pancake.item.PancakeItemSource;
import com.github.jenya705.pancake.item.event.PancakeItemEvent;
import org.bukkit.Material;

import java.util.function.Consumer;

/**
 *
 * Container of item used by pancake
 *
 * @param <T> Source type
 */
public interface PancakeItemContainer<T> {

    /**
     *
     * Return localized name of item (e.g. Diamond ore)
     *
     * @return localized name of item (e.g. Diamond ore)
     */
    String getName();

    /**
     *
     * Return id of item (e.g. minecraft:diamond_ore)
     *
     * @return id of item (e.g. minecraft:diamond_ore)
     */
    String getId();

    /**
     *
     * Return material which used to pretend item
     *
     * @return material which used to pretend item
     */
    Material getMaterial();

    /**
     *
     * Return source object of item
     *
     * @return Source object of item (has {@link PancakeItem} annotation)
     */
    T getSource();

    /**
     *
     * Return custom model data of item
     *
     * @return Item custom model data
     */
    int getCustomModelData();

    /**
     *
     * Return additional enchantment level
     *
     * @return additional enchantment level
     */
    int getAdditionalEnchantmentCost();

    /**
     *
     * Invoke handlers of given event with given source and arguments event
     *
     * @param event Event
     * @param source Source
     * @param consumer Handler function
     */
    void addHandler(Class<? extends PancakeItemEvent> event, PancakeItemSource source, Consumer<PancakeItemEvent> consumer);

    /**
     *
     * Add source handler of given event
     *
     * @param event Event
     * @param source Source
     */
    void invokeEvent(PancakeItemEvent event, PancakeItemSource source);

}
