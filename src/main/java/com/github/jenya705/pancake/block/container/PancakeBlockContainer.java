package com.github.jenya705.pancake.block.container;

import com.github.jenya705.pancake.block.event.PancakeBlockEvent;
import com.github.jenya705.pancake.item.container.PancakeItemContainer;
import org.bukkit.Material;

import java.util.function.Consumer;

public interface PancakeBlockContainer<T> {

    String getName();

    String getId();

    Material getItemMaterial();

    Material getBlockMaterial();

    PancakeItemContainer<T> getBlockItem();

    T getSource();

    int getNote();

    void addHandler(Class<? extends PancakeBlockEvent> event, Consumer<PancakeBlockEvent> handler);

    void invokeEvent(PancakeBlockEvent event);

}
