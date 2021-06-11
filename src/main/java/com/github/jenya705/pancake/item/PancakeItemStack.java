package com.github.jenya705.pancake.item;

import com.github.jenya705.pancake.enchantment.PancakeEnchantmentObject;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

import java.util.List;

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
     * @return Pancake enchantments
     */
    List<PancakeEnchantmentObject> getEnchantments();

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
     * @return {@link PancakeItemStack} if {@link PancakeItemStackImpl#canCreate(ItemStack)} is true or {@link BukkitPancakeItemStack}
     */
    static PancakeItemStack of(ItemStack bukkit) {
        return PancakeItemStackImpl.canCreate(bukkit) ? new PancakeItemStackImpl(bukkit) : new BukkitPancakeItemStack(bukkit);
    }

    /**
     * @param bukkit Bukkit ItemStack
     * @return {@link PancakeItemStack} if {@link PancakeItemStackImpl#canCreate(ItemStack)} is true or null
     */
    static PancakeItemStack safe(ItemStack bukkit) {
        return PancakeItemStackImpl.canCreate(bukkit) ? new PancakeItemStackImpl(bukkit) : null;
    }

}
