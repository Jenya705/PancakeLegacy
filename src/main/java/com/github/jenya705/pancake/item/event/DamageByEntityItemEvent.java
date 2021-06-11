package com.github.jenya705.pancake.item.event;

import com.github.jenya705.pancake.item.PancakeItemStack;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter(AccessLevel.PROTECTED)
public class DamageByEntityItemEvent implements PancakeItemEvent{

    private EntityDamageByEntityEvent bukkit;
    private PancakeItemStack itemStack;
    private boolean damager;

    public DamageByEntityItemEvent(EntityDamageByEntityEvent event, boolean damager) {
        setBukkit(event);
        setDamager(damager);
        setItemStack(PancakeItemStack.of(
                damager ? getActiveItem(event.getDamager()) : getActiveItem(event.getEntity()))
        );
    }

    protected ItemStack getActiveItem(Entity entity) {
        return entity instanceof LivingEntity ? ((LivingEntity) entity).getActiveItem() : null;
    }

}
