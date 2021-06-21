package com.github.jenya705.pancake.enchantment;

import com.github.jenya705.pancake.Pancake;
import com.github.jenya705.pancake.item.PancakeItemStack;
import com.github.jenya705.pancake.item.PancakeItemUtils;
import lombok.experimental.UtilityClass;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Pancake enchantment util functions
 */
@UtilityClass
public class PancakeEnchantmentUtils {

    /**
     *
     * Return enchantment container with given id
     *
     * @param id ID of enchantment
     * @return Enchantment container with given id
     */
    public static PancakeEnchantmentContainer<?> getEnchantmentContainer(String id) {
        return Pancake.getPlugin().getRegister().getEnchantmentContainer(id);
    }

    /**
     *
     * Return enchanted item
     *
     * @param itemStack Item
     * @param enchantmentContainer Container of enchantment
     * @param level Level
     * @return Enchanted item
     */
    public static ItemStack enchantItem(ItemStack itemStack, PancakeEnchantmentContainer<?> enchantmentContainer, int level) {
        if (PancakeItemUtils.isItemNone(itemStack)) return itemStack;
        itemStack.addUnsafeEnchantment(enchantmentContainer.getWrapper(), level);
        return itemStack;
    }

    /**
     *
     * Return all enchantments of item as {@link PancakeEnchantmentObject}
     *
     * @param itemStack Item
     * @return All enchantments of item
     */
    public static List<PancakeEnchantmentObject> getEnchantments(PancakeItemStack itemStack) {
        List<PancakeEnchantmentObject> array = new ArrayList<>();
        Map<Enchantment, Integer> enchantments = itemStack.getBukkit().getEnchantments();
        for (Map.Entry<Enchantment, Integer> enchantment: enchantments.entrySet()) {
            Enchantment enchantmentObject = enchantment.getKey();
            array.add(new PancakeEnchantmentObjectImpl(
                enchantmentObject instanceof PancakeEnchantmentWrapper ?
                    getEnchantmentContainer(((PancakeEnchantmentWrapper) enchantmentObject).getEnchantmentContainer().getId()) :
                        BukkitPancakeEnchantmentContainer.getVanillaEnchantment(enchantmentObject.getKey().getKey()),
                enchantment.getValue(), itemStack
            ));
        }
        return array;
    }

}
