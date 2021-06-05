package com.github.jenya705.pancake.item.object;

import com.github.jenya705.pancake.PancakeConfigurable;
import com.github.jenya705.pancake.data.PancakeSerializedData;
import com.github.jenya705.pancake.item.*;
import com.github.jenya705.pancake.item.event.ArmorEquipItemEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.PlayerInventory;

@PancakeItem(
        name = "Invisible Cloak",
        id = InvisibleCloakItem.id,
        material = Material.LEATHER_CHESTPLATE
)
public class InvisibleCloakItem implements Listener, PancakeItemListener, PancakeConfigurable {

    public static final String id = "pancake:invisible_cloak";

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("Hi!");
        event.getPlayer().getInventory().addItem(PancakeItemUtils.generateItemStack("pancake:invisible_cloak"));
    }

    @PancakeItemEventHandler
    public void armorEquip(ArmorEquipItemEvent event) {
        Player player = event.getBukkit().getPlayer();
        if (event.isOld()) { // Invisible cloak unequipped
            unequipCloak(event);
        }
        else { // Invisible cloak equipped
            equipCloak(event);
        }
    }

    @PancakeItemEventHandler(source = PancakeItemSource.CHESTPLATE)
    public void armorEquipOther(ArmorEquipItemEvent event) {
        if (event.getItemStack() == null || !event.getItemStack().isId(id)) return;
        
    }
    
    protected void equipCloak(ArmorEquipItemEvent event) {
        PancakeItemStack cloak = event.getItemStack();
    }
    
    protected void unequipCloak(ArmorEquipItemEvent event) {
        PancakeItemStack cloak = event.getItemStack();
    }
    
    protected boolean isSomeArmorEquipped(PlayerInventory inventory) {
        return  PancakeItemUtils.isItemNone(inventory.getHelmet()) &&
                PancakeItemUtils.isItemNone(inventory.getLeggings()) &&
                PancakeItemUtils.isItemNone(inventory.getBoots());
    }

    @Override
    public void load(PancakeSerializedData data) {

    }

    @Override
    public void save(PancakeSerializedData data) {

    }
}
