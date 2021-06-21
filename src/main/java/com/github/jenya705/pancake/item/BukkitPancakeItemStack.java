package com.github.jenya705.pancake.item;

import com.github.jenya705.pancake.enchantment.PancakeEnchantmentObject;
import com.github.jenya705.pancake.enchantment.PancakeEnchantmentUtils;
import com.github.jenya705.pancake.item.container.BukkitPancakeItemContainer;
import com.github.jenya705.pancake.item.container.PancakeItemContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

/**
 * {@link ItemStack} as {@link PancakeItemStack} with cut functionality like {@link #getItemContainer()}
 */
@Getter
@Setter(AccessLevel.PROTECTED)
public class BukkitPancakeItemStack implements PancakeItemStack {

    private ItemStack bukkit;
    private NBTItem nbt;
    private List<PancakeEnchantmentObject> enchantments;

    public BukkitPancakeItemStack(ItemStack bukkit) {
        setBukkit(bukkit);
        if (!PancakeItemUtils.isItemNone(bukkit)){
            setNbt(new NBTItem(bukkit));
            setEnchantments(PancakeEnchantmentUtils.getEnchantments(this));
        }
        else {
            setEnchantments(Collections.emptyList());
            setNbt(null);
        }
    }

    @Override
    public PancakeItemContainer<?> getItemContainer() {
        return new BukkitPancakeItemContainer(bukkit);
    }

    @Override
    public String getId() {
        return "minecraft:" + bukkit.getType().name();
    }

    @Override
    public boolean isId(String id) {
        return getId().equalsIgnoreCase(id);
    }
}
