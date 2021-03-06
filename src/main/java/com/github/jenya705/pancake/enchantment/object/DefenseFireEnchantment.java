package com.github.jenya705.pancake.enchantment.object;

import com.github.jenya705.pancake.PancakeConfigurable;
import com.github.jenya705.pancake.data.PancakeData;
import com.github.jenya705.pancake.enchantment.PancakeEnchantment;
import com.github.jenya705.pancake.enchantment.PancakeEnchantmentEventHandler;
import com.github.jenya705.pancake.enchantment.PancakeEnchantmentListener;
import com.github.jenya705.pancake.enchantment.PancakeEnchantmentObject;
import com.github.jenya705.pancake.enchantment.rarity.PancakeEnchantmentRarity;
import com.github.jenya705.pancake.item.PancakeItemSource;
import com.github.jenya705.pancake.item.event.DamageByEntityItemEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;

@PancakeEnchantment(
        id = "pancake:defense_fire",
        name = "Defense fire",
        target = EnchantmentTarget.ARMOR,
        rarity = PancakeEnchantmentRarity.RARE_VALUE,
        treasure = false,
        conflicts = {"minecraft:fire_protection"},
        maxLevel = 3
)
@Getter
@Setter(AccessLevel.PROTECTED)
public class DefenseFireEnchantment implements PancakeEnchantmentListener, PancakeConfigurable {

    private int ticks = 20;

    @PancakeEnchantmentEventHandler(source = PancakeItemSource.ARMOR)
    public void takeDamage(DamageByEntityItemEvent event, PancakeEnchantmentObject enchantmentObject) {
        if (event.isDamager()) return;
        Entity damager = event.getBukkit().getDamager();
        damager.setFireTicks(damager.getFireTicks() + getTicks() * enchantmentObject.getLevel());
    }

    @Override
    public void load(PancakeData data) {
        setTicks(data.getInteger("fireTicks", getTicks()));
    }

    @Override
    public void save(PancakeData data) {
        data.set("fireTicks", getTicks());
    }
}
