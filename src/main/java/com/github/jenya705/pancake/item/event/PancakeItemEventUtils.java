package com.github.jenya705.pancake.item.event;

import com.github.jenya705.pancake.PancakeUtils;
import com.github.jenya705.pancake.item.PancakeItemContainer;
import com.github.jenya705.pancake.item.PancakeItemSource;
import com.github.jenya705.pancake.item.PancakeItemStack;
import com.github.jenya705.pancake.item.PancakeItemUtils;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.function.Consumer;

@UtilityClass
public class PancakeItemEventUtils {

    public static void invokeEvent(PancakeItemEvent event, Player player) {
        if (event.getItemStack() != null) {
            invokeItemEvent(event.getItemStack(), event, PancakeItemSource.MAIN);
        }
        if (player != null) invokeNotMainSources(player.getInventory(), event);
    }

    @Deprecated
    public static Consumer<PancakeItemContainer<?>> defaultConsumer(PancakeItemEvent event, PancakeItemSource... sources) {
        return (container) -> {
            for (PancakeItemSource source: sources) {
                container.invokeEvent(event, source);
            }
        };
    }

    public static void invokeItemEvent(PancakeItemStack itemStack, PancakeItemEvent event, PancakeItemSource... sources) {
        if (itemStack == null || event == null) return;
        for (PancakeItemSource source: sources) {
            if (itemStack.getItemContainer() != null) itemStack.getItemContainer().invokeEvent(event, source);
            itemStack.getEnchantments().forEach(it ->
                it.getEnchantmentContainer().invokeEvent(event, source, it)
            );
        }
    }

    public static Consumer<PancakeItemContainer<?>> defaultConsumer(PancakeItemEvent event) {
        return defaultConsumer(event, PancakeItemSource.MAIN);
    }

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
