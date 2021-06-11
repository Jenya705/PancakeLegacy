package com.github.jenya705.pancake.item;

import com.github.jenya705.pancake.PancakeUtils;
import com.github.jenya705.pancake.enchantment.PancakeEnchantmentObject;
import com.github.jenya705.pancake.enchantment.PancakeEnchantmentObjectImpl;
import com.github.jenya705.pancake.enchantment.PancakeEnchantmentUtils;
import de.tr7zw.changeme.nbtapi.NBTCompoundList;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import io.papermc.paper.enchantments.EnchantmentRarity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter(AccessLevel.PROTECTED)
public class PancakeItemStackImpl implements PancakeItemStack {

    private PancakeItemContainer<?> itemContainer;
    private ItemStack bukkit;
    private NBTItem nbt;
    private String id;

    private List<PancakeEnchantmentObject> enchantments;

    /**
     * @see #canCreate(ItemStack)
     * @throws IllegalArgumentException if bukkit ItemStack is not PancakeItemStack
     * @param bukkit Bukkit ItemStack
     */
    public PancakeItemStackImpl(ItemStack bukkit) {
        setBukkit(bukkit);
        if (bukkit == null || bukkit.getType() == Material.AIR) throw new IllegalArgumentException("ItemStack is not PancakeItemStack");
        setNbt(new NBTItem(getBukkit()));
        if (!getNbt().hasKey("pancake_type")) throw new IllegalArgumentException("ItemStack is not PancakeItemStack");
        setItemContainer(PancakeItemUtils.getItemContainer(nbt.getString("pancake_type")));
        setId(getItemContainer().getID());
        setEnchantments(PancakeEnchantmentUtils.getEnchantments(getNbt(), this));
    }

    public static boolean canCreate(ItemStack bukkit) {
        return bukkit != null && bukkit.getType() != Material.AIR && new NBTItem(bukkit).hasKey("pancake_type");
    }

    @Override
    public boolean isId(String id) {
        return getId().equalsIgnoreCase(id);
    }
}
