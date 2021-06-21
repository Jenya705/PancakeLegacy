package com.github.jenya705.pancake.item.event;

import com.github.jenya705.pancake.item.PancakeItemSource;
import com.github.jenya705.pancake.item.PancakeItemStack;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

/**
 * Item event util functions
 */
@UtilityClass
public class PancakeItemEventUtils {

    /**
     *
     * Invoking all event sources
     *
     * @param event Event which will be invoked
     * @param player Player
     */
    public static void invokeEvent(PancakeItemEvent event, Player player) {
        if (event.getItemStack() != null) {
            invokeItemEvent(event.getItemStack(), event, PancakeItemSource.MAIN);
        }
        if (player != null) invokeNotMainSources(player.getInventory(), event);
    }

    /**
     *
     * Invoke only event with given sources
     *
     * @param itemStack Item
     * @param event Event
     * @param sources Sources
     */
    public static void invokeItemEvent(PancakeItemStack itemStack, PancakeItemEvent event, PancakeItemSource... sources) {
        if (itemStack == null || event == null) return;
        for (PancakeItemSource source: sources) {
            if (itemStack.getItemContainer() != null) itemStack.getItemContainer().invokeEvent(event, source);
            itemStack.getEnchantments().forEach(it ->
                    it.getEnchantmentContainer().invokeEvent(event, source, it)
            );
        }
    }

    /**
     *
     * Invoke event with all sources without main source
     *
     * @param playerInventory Player Inventory
     * @param event Event
     */
    public static void invokeNotMainSources(PlayerInventory playerInventory, PancakeItemEvent event) {
        // Armor
        invokeItemEvent(PancakeItemStack.of(playerInventory.getHelmet()), event, PancakeItemSource.HELMET, PancakeItemSource.ARMOR);
        invokeItemEvent(PancakeItemStack.of(playerInventory.getChestplate()), event, PancakeItemSource.CHESTPLATE, PancakeItemSource.ARMOR);
        invokeItemEvent(PancakeItemStack.of(playerInventory.getLeggings()), event, PancakeItemSource.LEGGINGS, PancakeItemSource.ARMOR);
        invokeItemEvent(PancakeItemStack.of(playerInventory.getBoots()), event, PancakeItemSource.BOOTS, PancakeItemSource.ARMOR);
        // Main hand
        invokeItemEvent(PancakeItemStack.of(playerInventory.getItemInMainHand()), event, PancakeItemSource.MAIN_HAND);
        // Off hand
        invokeItemEvent(PancakeItemStack.of(playerInventory.getItemInOffHand()), event, PancakeItemSource.OFF_HAND);
    }

}
