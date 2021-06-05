package com.github.jenya705.pancake.item;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

public interface PancakeItemStack {

    /**
     * @return Bukkit itemStack implementation
     */
    ItemStack getBukkit();

    /**
     * @return NBT of item
     */
    NBTItem getNbt();

    /**
     * @return Container of this item
     */
    PancakeItemContainer<?> getItemContainer();

    /**
     * Type nbt key is 'pancake_type'
     * @return Pancake type of object, if not pancake item returns null
     */
    String getId();

    /**
     * @param id Pancake type
     * @return is param type is pancake type of this item
     */
    boolean isId(String id);

    /**
     * @param bukkit Bukkit ItemStack
     * @return PancakeItemStack or null if itemStack is not PancakeItemStack
     */
    static PancakeItemStack of(ItemStack bukkit) {
        return PancakeItemStackImpl.canCreate(bukkit) ? new PancakeItemStackImpl(bukkit) : null;
    }

}
