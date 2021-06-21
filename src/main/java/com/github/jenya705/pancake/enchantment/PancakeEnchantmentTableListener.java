package com.github.jenya705.pancake.enchantment;

import com.github.jenya705.pancake.Pancake;
import com.github.jenya705.pancake.enchantment.weight.PancakeEnchantmentWeightObject;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

import java.util.List;

public class PancakeEnchantmentTableListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void enchantComplete(EnchantItemEvent event) {
        if (event.isCancelled() || event.getEnchantsToAdd().isEmpty()) return;
        Pancake pancake = Pancake.getPlugin();
        List<PancakeEnchantmentWeightObject> toAdd = pancake.getEnchantmentWeightContainer()
                .getLineRandomEnchantmentContainersForEnchant(event.getItem(), event.whichButton(), event.getExpLevelCost());
        event.getEnchantsToAdd().clear();
        toAdd.forEach(it ->
            event.getEnchantsToAdd().put(it.getEnchantmentContainer().getWrapper(), it.getLevel())
        );
    }

}
