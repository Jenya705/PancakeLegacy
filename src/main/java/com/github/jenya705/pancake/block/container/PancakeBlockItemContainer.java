package com.github.jenya705.pancake.block.container;

import com.github.jenya705.pancake.Pancake;
import com.github.jenya705.pancake.item.PancakeItemSource;
import com.github.jenya705.pancake.item.container.EventablePancakeItemContainer;
import com.github.jenya705.pancake.item.event.BlockPlaceItemEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Objects;

@Getter
@Setter(AccessLevel.PROTECTED)
public class PancakeBlockItemContainer<T> extends EventablePancakeItemContainer<T> {

    private PancakeBlockContainer<T> block;
    private int customModelData;

    public PancakeBlockItemContainer(PancakeBlockContainer<T> block) {
        Pancake pancake = Pancake.getPlugin();
        setBlock(block);
        setCustomModelData(pancake.getCustomModelDataContainer().getCustomModelData(this));
        addHandler(BlockPlaceItemEvent.class, PancakeItemSource.MAIN, (it) -> place((BlockPlaceItemEvent) it));
    }

    @Override
    public String getName() {
        return getBlock().getName();
    }

    @Override
    public String getId() {
        return getBlock().getId();
    }

    @Override
    public Material getMaterial() {
        return getBlock().getItemMaterial();
    }

    @Override
    public T getSource() {
        return getBlock().getSource();
    }

    @Override
    public int getAdditionalEnchantmentCost() {
        return 0; // Disable, because blocks can not be enchanted
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public void place(BlockPlaceItemEvent event) {
        Block block = event.getBlock();
        block.setType(getBlock().getBlockMaterial());
        
    }

}
