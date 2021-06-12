package com.github.jenya705.pancake.item.event;

import com.github.jenya705.pancake.item.PancakeItemStack;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.entity.EntityPickupItemEvent;

/**
 * Present {@link EntityPickupItemEvent} as {@link PancakeItemEvent}
 */
@Getter
@Setter(AccessLevel.PROTECTED)
public class PickupItemEvent implements PancakeItemEvent {

    private EntityPickupItemEvent bukkit;
    private PancakeItemStack itemStack;

    public PickupItemEvent(EntityPickupItemEvent event) {
        setBukkit(event);
        setItemStack(PancakeItemStack.of(getBukkit().getItem().getItemStack()));
    }

    /**
     * @return Item which pickup entity
     */
    @Override
    public PancakeItemStack getItemStack() {
        return itemStack;
    }
}
