package com.github.jenya705.pancake.item.event;

import com.github.jenya705.pancake.item.PancakeItemStack;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Present {@link PlayerInteractEvent} as {@link PancakeItemEvent}
 */
@Getter
@Setter(AccessLevel.PROTECTED)
public class InteractItemEvent implements PancakeItemEvent {

    private PlayerInteractEvent bukkit;
    private PancakeItemStack itemStack;

    public InteractItemEvent(PlayerInteractEvent event) {
        setBukkit(event);
        setItemStack(PancakeItemStack.of(getBukkit().getItem()));
    }

}
