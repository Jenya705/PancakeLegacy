package com.github.jenya705.pancake.enchantment;

import com.github.jenya705.pancake.item.PancakeItemStack;
import io.papermc.paper.enchantments.EnchantmentRarity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@Getter
@Setter(AccessLevel.PROTECTED)
public class PancakeEnchantmentWrapper extends Enchantment {

    private static final Set<EquipmentSlot> FULL_EQUIPMENT_SLOTS = Set.of(EquipmentSlot.values());

    private PancakeEnchantmentContainer<?> enchantmentContainer;

    public PancakeEnchantmentWrapper(PancakeEnchantmentContainer<?> enchantmentContainer) {
        super(NamespacedKey.minecraft(enchantmentContainer.getId().split(":")[1]));
        setEnchantmentContainer(enchantmentContainer);
    }

    @Override
    public @NotNull String getName() {
        return getKey().getKey();
    }

    @Override
    public int getMaxLevel() {
        return getEnchantmentContainer().getMaxLevel();
    }

    @Override
    public int getStartLevel() {
        return getEnchantmentContainer().getStartLevel();
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return getEnchantmentContainer().getTarget();
    }

    @Override
    public boolean isTreasure() {
        return getEnchantmentContainer().isTreasure();
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment other) {
        return getEnchantmentContainer().isConflict(other.getKey().getNamespace()) &&
                (!(other instanceof PancakeEnchantmentWrapper) || ((PancakeEnchantmentWrapper) other).getEnchantmentContainer()
                        .isConflict(getEnchantmentContainer().getId()));
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item) {
        return getEnchantmentContainer().canApply(PancakeItemStack.of(item));
    }

    @Override
    public @NotNull Component displayName(int level) {
        return Component.text(getEnchantmentContainer().getName());
    }

    @Override
    public boolean isTradeable() {
        return getEnchantmentContainer().isTradeable();
    }

    @Override
    public boolean isDiscoverable() {
        return getEnchantmentContainer().isDiscoverable();
    }

    @Override
    public @NotNull EnchantmentRarity getRarity() {
        return getEnchantmentContainer().getRarity();
    }

    @Override
    public float getDamageIncrease(int level, @NotNull EntityCategory entityCategory) {
        return 0;
    }

    @Override
    public @NotNull Set<EquipmentSlot> getActiveSlots() {
        return FULL_EQUIPMENT_SLOTS;
    }
}
