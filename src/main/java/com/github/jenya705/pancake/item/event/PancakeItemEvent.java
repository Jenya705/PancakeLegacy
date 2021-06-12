package com.github.jenya705.pancake.item.event;

import com.github.jenya705.pancake.item.PancakeItemStack;
import org.bukkit.event.Event;

/**
 *
 * Main interface for all events with pancake items
 */
public interface PancakeItemEvent {

    /**
     * @return Bukkit event
     */
    Event getBukkit();

    /**
     * @return Item attached to this event
     */
    PancakeItemStack getItemStack();

}
