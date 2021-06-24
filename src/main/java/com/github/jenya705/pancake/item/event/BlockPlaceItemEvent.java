package com.github.jenya705.pancake.item.event;

import com.github.jenya705.pancake.block.event.PancakeBlockEvent;
import com.github.jenya705.pancake.item.PancakeItemStack;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;

@Getter
@Setter(AccessLevel.PROTECTED)
public class BlockPlaceItemEvent implements PancakeItemEvent, PancakeBlockEvent {

    private BlockPlaceEvent bukkit;
    private PancakeItemStack itemStack;

    public BlockPlaceItemEvent(BlockPlaceEvent event) {
        setBukkit(event);
        setItemStack(PancakeItemStack.of(event.getItemInHand()));
    }

    @Override
    public Block getBlock() {
        return getBukkit().getBlockPlaced();
    }
}
