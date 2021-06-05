package com.github.jenya705.pancake.item.event;

import com.github.jenya705.pancake.PancakeUtils;
import com.github.jenya705.pancake.item.PancakeItemContainer;
import com.github.jenya705.pancake.item.PancakeItemSource;
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
            PancakeUtils.consumeIfNotNull(event.getItemStack().getItemContainer(), defaultConsumer(event));
        }
        if (player != null) invokeNotMainSources(player.getInventory(), event);
    }

    public static Consumer<PancakeItemContainer<?>> defaultConsumer(PancakeItemEvent event, PancakeItemSource source) {
        return (container) -> container.invokeEvent(event, source);
    }

    public static Consumer<PancakeItemContainer<?>> defaultConsumer(PancakeItemEvent event) {
        return defaultConsumer(event, PancakeItemSource.MAIN);
    }

    public static void invokeNotMainSources(PlayerInventory playerInventory, PancakeItemEvent event) {
        // Armor
        PancakeUtils.consumeIfNotNull(PancakeItemUtils.getItemContainer(playerInventory.getHelmet()), defaultConsumer(event, PancakeItemSource.HELMET));
        PancakeUtils.consumeIfNotNull(PancakeItemUtils.getItemContainer(playerInventory.getChestplate()), defaultConsumer(event, PancakeItemSource.CHESTPLATE));
        PancakeUtils.consumeIfNotNull(PancakeItemUtils.getItemContainer(playerInventory.getLeggings()), defaultConsumer(event, PancakeItemSource.LEGGINGS));
        PancakeUtils.consumeIfNotNull(PancakeItemUtils.getItemContainer(playerInventory.getBoots()), defaultConsumer(event, PancakeItemSource.BOOTS));
        // Main Hand
        PancakeUtils.consumeIfNotNull(PancakeItemUtils.getItemContainer(playerInventory.getItemInMainHand()), defaultConsumer(event, PancakeItemSource.MAIN_HAND));
        // Off Hand
        PancakeUtils.consumeIfNotNull(PancakeItemUtils.getItemContainer(playerInventory.getItemInOffHand()), defaultConsumer(event, PancakeItemSource.OFF_HAND));
    }

}
