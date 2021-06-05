package com.github.jenya705.pancake.item.event;

import com.github.jenya705.pancake.event.armorequip.BukkitArmorEquipEvent;
import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.github.jenya705.pancake.item.PancakeItemStack;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

/**
 * Present {@link BukkitArmorEquipEvent} as {@link PancakeItemEvent}
 */
@Getter
@Setter(AccessLevel.PROTECTED)
public class ArmorEquipItemEvent implements PancakeItemEvent {

    private BukkitArmorEquipEvent bukkit;
    private boolean old;
    private PancakeItemStack oldItemStack;
    private PancakeItemStack newItemStack;

    public ArmorEquipItemEvent(BukkitArmorEquipEvent event, boolean old) {
        setBukkit(event);
        setOld(old);

        setOldItemStack(PancakeItemStack.of(event.getOldArmorPiece()));
        setNewItemStack(PancakeItemStack.of(event.getNewArmorPiece()));

    }

    /**
     * @return is ItemStack attached to this event is a new item {@link #getItemStack()}
     */
    public boolean isNew() {
        return !isOld();
    }

    /**
     * @return is ItemStack attached to this event is an old item {@link #getItemStack()}
     */
    public boolean isOld() {
        return old;
    }

    @Override
    public PancakeItemStack getItemStack() {
        return isOld() ? getOldItemStack() : getNewItemStack();
    }
}
