package com.github.jenya705.pancake.item;

import de.tr7zw.nbtapi.NBTItem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter(AccessLevel.PROTECTED)
public class PancakeItemStackImpl implements PancakeItemStack {

    private PancakeItemContainer<?> itemContainer;
    private ItemStack bukkit;
    private NBTItem nbt;
    private String id;

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
    }

    public static boolean canCreate(ItemStack bukkit) {
        return bukkit != null && bukkit.getType() != Material.AIR && new NBTItem(bukkit).hasKey("pancake_type");
    }

    @Override
    public boolean isId(String id) {
        return getId().equalsIgnoreCase(id);
    }
}
