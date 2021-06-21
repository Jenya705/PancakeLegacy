package com.github.jenya705.pancake.item;

import com.github.jenya705.pancake.enchantment.PancakeEnchantmentObject;
import com.github.jenya705.pancake.enchantment.PancakeEnchantmentUtils;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

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
        if (!getNbt().hasKey(PancakeItemUtils.pancakeType)) throw new IllegalArgumentException("ItemStack is not PancakeItemStack");
        setItemContainer(PancakeItemUtils.getSafeItemContainer(nbt.getString(PancakeItemUtils.pancakeType)));
        setId(getItemContainer().getID());
        setEnchantments(PancakeEnchantmentUtils.getEnchantments(this));
    }

    public static boolean canCreate(ItemStack bukkit) {
        return bukkit != null && bukkit.getType() != Material.AIR && new NBTItem(bukkit).hasKey("pancake_type");
    }

    @Override
    public boolean isId(String id) {
        return getId().equalsIgnoreCase(id);
    }
}
