package com.github.jenya705.pancake.item;

import com.github.jenya705.pancake.item.event.PancakeItemEvent;
import org.bukkit.Material;
import org.bukkit.event.Event;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface PancakeItemContainer<T> {

    String getName();

    String getID();

    Material getMaterial();

    /**
     * @return Source object of item (has {@link PancakeItem} annotation)
     */
    T getSource();

    void addHandler(Class<? extends PancakeItemEvent> event, PancakeItemSource source, Consumer<PancakeItemEvent> consumer);

    void invokeEvent(PancakeItemEvent event, PancakeItemSource source);

}
