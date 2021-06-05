package com.github.jenya705.pancake.item;

import com.github.jenya705.pancake.Pancake;
import de.tr7zw.nbtapi.NBTItem;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Locale;

@UtilityClass
public class PancakeItemUtils {

    public static PancakeItemContainer<?> getItemContainer(String id) {
        Pancake pancake = Pancake.getPlugin();
        return pancake.getRegister().getItemContainer(id);
    }

    public static PancakeItemContainer<?> getItemContainer(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) return null;
        NBTItem nbt = new NBTItem(itemStack);
        return nbt.hasKey("pancake_type") ? getItemContainer(nbt.getString("pancake_type")) : null;
    }

    public static ItemStack generateItemStack(String id) {
        return generateItemStack(getItemContainer(id));
    }

    public static ItemStack generateItemStack(PancakeItemContainer<?> itemContainer) {
        ItemStack itemStack = new ItemStack(itemContainer.getMaterial());
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(itemContainer.getName()));
        itemStack.setItemMeta(itemMeta);
        NBTItem nbt = new NBTItem(itemStack);
        nbt.setString("pancake_type", itemContainer.getID());
        return nbt.getItem();
    }

    public static boolean isItemNone(ItemStack item) {
        return item == null || item.getType() == Material.AIR;
    }

}
