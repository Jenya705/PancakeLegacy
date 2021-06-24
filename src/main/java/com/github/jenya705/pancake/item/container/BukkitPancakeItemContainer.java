package com.github.jenya705.pancake.item.container;

import com.github.jenya705.pancake.Pancake;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;
import java.util.Objects;

@Getter
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
public class BukkitPancakeItemContainer extends EventablePancakeItemContainer<Void> {

    private ItemStack itemStack;

    @Override
    public String getName() {
        return itemStack.getI18NDisplayName();
    }

    @Override
    public String getId() {
        return "minecraft:" + itemStack.getType().name().toLowerCase(Locale.ROOT);
    }

    @Override
    public Material getMaterial() {
        return itemStack.getType();
    }

    @Override
    public Void getSource() {
        return null;
    }

    @Override
    public int getCustomModelData() {
        return itemStack.hasItemMeta() ? itemStack.getItemMeta().hasCustomModelData() ? itemStack.getItemMeta().getCustomModelData() : 0 : 0;
    }

    @Override
    public int getAdditionalEnchantmentCost() {
        return Pancake.getPlugin().getNms().itemCMethod(itemStack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
