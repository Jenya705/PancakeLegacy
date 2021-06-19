package com.github.jenya705.pancake.enchantment;

import com.github.jenya705.pancake.Pancake;
import com.github.jenya705.pancake.util.Pair;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PancakeEnchantmentTableListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void enchantComplete(EnchantItemEvent event) {
        if (event.isCancelled() || event.getEnchantsToAdd().isEmpty()) return;
        Pancake pancake = Pancake.getPlugin();
        List<Pair<PancakeEnchantmentContainer<?>, Integer>> toAdd = pancake.getEnchantmentWeightContainer()
                .getLineRandomEnchantmentContainersForEnchant(event.getItem(), event.whichButton(), event.getExpLevelCost());
        event.getEnchantsToAdd().clear();
        toAdd.forEach(it ->
            event.getEnchantsToAdd().put(it.left().getWrapper(), it.right())
        );
    }

}
