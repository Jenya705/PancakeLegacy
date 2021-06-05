package com.github.jenya705.pancake.item;

import com.github.jenya705.pancake.event.armorequip.BukkitArmorEquipEvent;
import com.github.jenya705.pancake.item.event.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PancakeBukkitItemListener implements Listener {

    @EventHandler
    public void pickup(EntityPickupItemEvent event) {
        PickupItemEvent pancakeEvent = new PickupItemEvent(event);
        PancakeItemEventUtils.invokeEvent(pancakeEvent, event.getEntity() instanceof Player ? (Player) event.getEntity() : null);
    }

    @EventHandler
    public void armorChange(BukkitArmorEquipEvent event) {
        ArmorEquipItemEvent pancakeOldEvent = new ArmorEquipItemEvent(event, true);
        ArmorEquipItemEvent pancakeNewEvent = new ArmorEquipItemEvent(event, false);
        PancakeItemEventUtils.invokeEvent(pancakeOldEvent, event.getPlayer());
        PancakeItemEventUtils.invokeEvent(pancakeNewEvent, event.getPlayer());
    }

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        InteractItemEvent pancakeEvent = new InteractItemEvent(event);
        PancakeItemEventUtils.invokeEvent(pancakeEvent, event.getPlayer());
    }

}
