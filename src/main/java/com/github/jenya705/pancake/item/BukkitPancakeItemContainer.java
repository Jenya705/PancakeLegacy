package com.github.jenya705.pancake.item;

import com.github.jenya705.pancake.Pancake;
import com.github.jenya705.pancake.item.event.PancakeItemEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;
import java.util.function.Consumer;

@Getter
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
public class BukkitPancakeItemContainer implements PancakeItemContainer<Void> {

    private ItemStack itemStack;

    @Override
    public String getName() {
        return itemStack.getI18NDisplayName();
    }

    @Override
    public String getID() {
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
    public int getAdditionalEnchantmentLevel() {
        return Pancake.getPlugin().getNms().itemCMethod(itemStack);
    }

    @Override
    public void addHandler(Class<? extends PancakeItemEvent> event, PancakeItemSource source, Consumer<PancakeItemEvent> consumer) {
        /* Empty */
    }

    @Override
    public void invokeEvent(PancakeItemEvent event, PancakeItemSource source) {
        /* Empty */
    }
}
