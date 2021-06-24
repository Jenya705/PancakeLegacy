package com.github.jenya705.pancake.block.container;

import com.github.jenya705.pancake.block.PancakeBlock;
import com.github.jenya705.pancake.item.container.PancakeItemContainer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

@Getter
@Setter(AccessLevel.PROTECTED)
public class PancakeBlockContainerImpl<T> extends EventablePancakeBlockContainer<T> {

    private String name;
    private String id;
    private Material itemMaterial;
    private Material blockMaterial;
    private T source;
    private PancakeItemContainer<T> blockItem;
    private int note;

    public PancakeBlockContainerImpl(T source, PancakeBlock annotation) {
        setName(annotation.name());
        setId(annotation.id());
        setItemMaterial(annotation.itemMaterial());
        setBlockMaterial(annotation.blockMaterial());
        setSource(source);
        setBlockItem(new PancakeBlockItemContainer<>(this));
    }

}
