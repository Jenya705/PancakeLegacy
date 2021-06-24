package com.github.jenya705.pancake.block.event;

import org.bukkit.block.Block;
import org.bukkit.event.Event;

public interface PancakeBlockEvent {

    Event getBukkit();

    Block getBlock();

}
