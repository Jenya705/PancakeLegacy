package com.github.jenya705.pancake.item;

import com.github.jenya705.pancake.enchantment.PancakeEnchantmentObject;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface PancakeItemStack {

    /**
     *
     * Return bukkit item stack implementation
     *
     * @return Bukkit item stack implementation
     */
    ItemStack getBukkit();

    /**
     *
     * Return nbt of item
     *
     * @return NBT of item
     */
    NBTItem getNbt();

    /**
     *
     * Return container of this item
     *
     * @return Container of this item
     */
    PancakeItemContainer<?> getItemContainer();

    /**
     *
     * Return all enchantments of item
     *
     * @return All Enchantment as {@link PancakeEnchantmentObject}
     */
    List<PancakeEnchantmentObject> getEnchantments();

    /**
     *
     * Return id of item
     *
     * @return id of item
     */
    String getId();

    /**
     *
     * Checks if given id is id of this item
     *
     * @param id id
     * @return true if given id is id of this item
     */
    boolean isId(String id);

    /**
     *
     * Return {@link ItemStack} as {@link PancakeItemStack} with not null value
     *
     * @param bukkit Bukkit ItemStack
     * @return {@link PancakeItemStack} if {@link PancakeItemStackImpl#canCreate(ItemStack)} is true or {@link BukkitPancakeItemStack}
     */
    static PancakeItemStack of(ItemStack bukkit) {
        return PancakeItemStackImpl.canCreate(bukkit) ? new PancakeItemStackImpl(bukkit) : new BukkitPancakeItemStack(bukkit);
    }

    /**
     *
     * Return {@link ItemStack} as {@link PancakeItemStack} but if this item is not pancake item it will return null
     *
     * @param bukkit Bukkit ItemStack
     * @return {@link PancakeItemStack} if {@link PancakeItemStackImpl#canCreate(ItemStack)} is true or null
     */
    static PancakeItemStack safe(ItemStack bukkit) {
        return PancakeItemStackImpl.canCreate(bukkit) ? new PancakeItemStackImpl(bukkit) : null;
    }

}
