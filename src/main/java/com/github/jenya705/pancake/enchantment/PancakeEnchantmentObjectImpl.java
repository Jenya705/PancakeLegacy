package com.github.jenya705.pancake.enchantment;

import com.github.jenya705.pancake.item.PancakeItemStack;
import io.papermc.paper.enchantments.EnchantmentRarity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
public class PancakeEnchantmentObjectImpl implements PancakeEnchantmentObject {

    private PancakeEnchantmentContainer<?> enchantmentContainer;
    private int level;
    private PancakeItemStack itemStack;

}

