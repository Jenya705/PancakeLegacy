package com.github.jenya705.pancake.item;

import com.github.jenya705.pancake.Pancake;
import com.github.jenya705.pancake.item.container.PancakeItemContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Pancake item util functions
 */
@UtilityClass
public class PancakeItemUtils {

    public static final String pancakeType = "pancake_type";

    /**
     *
     * Return container of item with given id
     *
     * @param id id of item
     * @return container of item with given id
     */
    public static PancakeItemContainer<?> getSafeItemContainer(String id) {
        Pancake pancake = Pancake.getPlugin();
        return pancake.getRegister().getItemContainer(id);
    }

    /**
     *
     * Return container of given item
     * If item is not pancake item it will return null
     *
     * @param itemStack Item
     * @return Container of given item
     */
    public static PancakeItemContainer<?> getSafeItemContainer(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) return null;
        NBTItem nbt = new NBTItem(itemStack);
        return nbt.hasKey(pancakeType) ? getSafeItemContainer(nbt.getString(pancakeType)) : null;
    }

    /**
     *
     * Return generated pancake item with given id
     *
     * @param id id of item
     * @return generated pancake item with given id
     */
    public static ItemStack generateItemStack(String id) {
        return generateItemStack(getSafeItemContainer(id));
    }

    /**
     *
     * Return generated pancake item with given item container
     *
     * @param itemContainer container of item
     * @return generated pancake item with given item container
     */
    public static ItemStack generateItemStack(PancakeItemContainer<?> itemContainer) {
        ItemStack itemStack = new ItemStack(itemContainer.getMaterial());
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(itemContainer.getName()));
        itemMeta.setCustomModelData(itemContainer.getCustomModelData());
        itemStack.setItemMeta(itemMeta);
        NBTItem nbt = new NBTItem(itemStack);
        nbt.setString(pancakeType, itemContainer.getId());
        return nbt.getItem();
    }

    /**
     *
     * Checks if item is none (air or null)
     *
     * @param item Item
     * @return true if item is none (air or null)
     */
    public static boolean isItemNone(ItemStack item) {
        return item == null || item.getType() == Material.AIR;
    }

}
