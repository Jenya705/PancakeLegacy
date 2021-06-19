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

@UtilityClass
public class PancakeEnchantmentUtils {

    public static PancakeEnchantmentContainer<?> getEnchantmentContainer(String id) {
        return Pancake.getPlugin().getRegister().getEnchantmentContainer(id);
    }

    public static ItemStack enchantItem(ItemStack itemStack, PancakeEnchantmentContainer<?> enchantmentContainer, int level) {
        if (PancakeItemUtils.isItemNone(itemStack)) return itemStack;
        itemStack.addUnsafeEnchantment(enchantmentContainer.getWrapper(), level);
        return itemStack;
    }

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
