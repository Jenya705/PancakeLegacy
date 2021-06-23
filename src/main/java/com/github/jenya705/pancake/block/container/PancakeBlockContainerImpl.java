package com.github.jenya705.pancake.block.container;

import com.github.jenya705.pancake.block.PancakeBlock;
import com.github.jenya705.pancake.item.container.PancakeItemContainer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

@Getter
@Setter(AccessLevel.PROTECTED)
public class PancakeBlockContainerImpl<T> implements PancakeBlockContainer<T> {

    private String name;
    private String id;
    private Material material;
    private T source;
    private PancakeItemContainer<T> blockItem;
    private int note;

    public PancakeBlockContainerImpl(T source, PancakeBlock annotation) {
        setName(annotation.name());
        setId(annotation.id());
        setMaterial(annotation.material());
        setSource(source);
        setBlockItem(new PancakeBlockItemContainer<>(this));
    }

}
