package com.github.jenya705.pancake.item;

import com.github.jenya705.pancake.item.event.PancakeItemEvent;
import org.bukkit.Material;

import java.util.function.Consumer;

public interface PancakeItemContainer<T> {

    String getName();

    String getID();

    Material getMaterial();

    /**
     * @return Source object of item (has {@link PancakeItem} annotation)
     */
    T getSource();

    int getCustomModelData();

    int getAdditionalEnchantmentLevel();

    void addHandler(Class<? extends PancakeItemEvent> event, PancakeItemSource source, Consumer<PancakeItemEvent> consumer);

    void invokeEvent(PancakeItemEvent event, PancakeItemSource source);

}
