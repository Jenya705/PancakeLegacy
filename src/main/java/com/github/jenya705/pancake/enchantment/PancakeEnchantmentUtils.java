package com.github.jenya705.pancake.enchantment;

import com.github.jenya705.pancake.Pancake;
import com.github.jenya705.pancake.item.PancakeItemStack;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTCompoundList;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTListCompound;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class PancakeEnchantmentUtils {

    public static PancakeEnchantmentContainer<?> getEnchantmentContainer(String id) {
        return Pancake.getPlugin().getRegister().getEnchantContainer(id);
    }

    public static List<PancakeEnchantmentObject> getEnchantments(ItemStack itemStack) {
        return getEnchantments(new NBTItem(itemStack), PancakeItemStack.of(itemStack));
    }

    public static List<PancakeEnchantmentObject> getEnchantments(NBTItem nbt, PancakeItemStack pancakeItemStack) {
        List<PancakeEnchantmentObject> enchantments = new ArrayList<>();
        NBTCompoundList enchantmentsNBT = nbt.getCompoundList("pancakeEnchantments");
        for (NBTListCompound compound: enchantmentsNBT) {
            enchantments.add(new PancakeEnchantmentObjectImpl(
                    PancakeEnchantmentUtils.getEnchantmentContainer(compound.getString("id")),
                    compound.getInteger("level"),
                    pancakeItemStack
            ));
        }
        return enchantments;
    }

    public static ItemStack enchantItem(ItemStack itemStack, PancakeEnchantmentContainer<?> enchantmentContainer, int level) {
        NBTItem nbt = new NBTItem(itemStack);
        NBTCompound compound = nbt.getCompoundList("pancakeEnchantments").addCompound();
        compound.setString("id", enchantmentContainer.getId());
        compound.setInteger("level", level);
        return nbt.getItem();
    }

    public static ItemStack enchantItem(ItemStack itemStack, String id, int level) {
        return enchantItem(itemStack, Pancake.getPlugin().getRegister().getEnchantContainer(id), level);
    }

}
