package com.github.jenya705.pancake.block.container;

import com.github.jenya705.pancake.item.container.PancakeItemContainer;
import org.bukkit.Material;

public interface PancakeBlockContainer<T> {

    String getName();

    String getId();

    Material getMaterial();

    PancakeItemContainer<T> getBlockItem();

    T getSource();

    int getNote();

}
