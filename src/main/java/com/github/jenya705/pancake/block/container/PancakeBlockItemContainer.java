package com.github.jenya705.pancake.block.container;

import com.github.jenya705.pancake.Pancake;
import com.github.jenya705.pancake.item.container.EventablePancakeItemContainer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

@Getter
@Setter(AccessLevel.PROTECTED)
public class PancakeBlockItemContainer<T> extends EventablePancakeItemContainer<T> {

    private PancakeBlockContainer<T> block;
    private int customModelData;

    public PancakeBlockItemContainer(PancakeBlockContainer<T> block) {
        Pancake pancake = Pancake.getPlugin();
        setBlock(block);
        setCustomModelData(pancake.getCustomModelDataContainer().getCustomModelData(this));
    }

    @Override
    public String getName() {
        return getBlock().getName();
    }

    @Override
    public String getID() {
        return getBlock().getId();
    }

    @Override
    public Material getMaterial() {
        return getBlock().getMaterial();
    }

    @Override
    public T getSource() {
        return getBlock().getSource();
    }

    @Override
    public int getAdditionalEnchantmentCost() {
        return 0; // Disable, because blocks can not be enchanted
    }
}
