package com.github.jenya705.pancake.nms;

import org.bukkit.craftbukkit.v1_16_R3.enchantments.CraftEnchantment;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class PancakeNMSImpl implements PancakeNMS {

    @Override
    public int itemCMethod(ItemStack itemStack) {
        return CraftItemStack.asNMSCopy(itemStack).getItem().c();
    }

    @Override
    public int enchantmentAMethodInteger(Enchantment enchantment, int level) {
        return CraftEnchantment.getRaw(enchantment).a(level);
    }

    @Override
    public int enchantmentBMethodInteger(Enchantment enchantment, int level) {
        return CraftEnchantment.getRaw(enchantment).b(level);
    }
}
