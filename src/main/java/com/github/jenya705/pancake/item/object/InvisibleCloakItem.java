package com.github.jenya705.pancake.item.object;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.Pair;
import com.github.jenya705.pancake.Pancake;
import com.github.jenya705.pancake.PancakeConfigurable;
import com.github.jenya705.pancake.PancakeMessage;
import com.github.jenya705.pancake.data.PancakeData;
import com.github.jenya705.pancake.event.armorequip.ArmorType;
import com.github.jenya705.pancake.item.*;
import com.github.jenya705.pancake.item.event.ArmorEquipItemEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

@PancakeItem(
        name = "Cloak of Invisibility",
        id = InvisibleCloakItem.id,
        material = Material.LEATHER_CHESTPLATE
)
@Getter
@Setter(AccessLevel.PROTECTED)
public class InvisibleCloakItem implements Listener, PancakeItemListener, PancakeConfigurable {

    public static final String id = "pancake:invisible_cloak";

    private List<Integer> entityIds = new ArrayList<>();

    private PotionEffect potionEffect = new PotionEffect(PotionEffectType.INVISIBILITY, 10000000, 0, true, false, false);
    private PancakeMessage equipCloakMessage = PancakeMessage.of("&aCloak equipped");
    private PancakeMessage unequipCloakMessage = PancakeMessage.of("&eCloak unequipped");
    private PancakeMessage loseEffectCloakMessage = PancakeMessage.of("&cCloak disabled because another armor equipped");
    private PancakeMessage enableEffectCloakMessage = PancakeMessage.of("&aCloak enabled");

    public InvisibleCloakItem() {
        Pancake.getPlugin().getProtocolManager().addPacketListener(new PacketAdapter(
                Pancake.getPlugin(),
                ListenerPriority.NORMAL,
                PacketType.Play.Server.ENTITY_EQUIPMENT) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                int entityId = packet.getIntegers().read(0);
                if (event.getPlayer().getEntityId() != entityId && getEntityIds().contains(entityId)) {
                    packet.getSlotStackPairLists()
                            .write(0, Arrays.asList(
                                    new Pair<>(EnumWrappers.ItemSlot.MAINHAND, new ItemStack(Material.AIR)),
                                    new Pair<>(EnumWrappers.ItemSlot.OFFHAND, new ItemStack(Material.AIR)),
                                    new Pair<>(EnumWrappers.ItemSlot.CHEST, new ItemStack(Material.AIR))
                            ));
                }
            }
        });
    }

    @PancakeItemEventHandler
    public void armorEquip(ArmorEquipItemEvent event) {
        Player player = event.getBukkit().getPlayer();
        if (isSomeArmorEquipped(player.getInventory())) {
            player.sendActionBar(Component.text(getLoseEffectCloakMessage().getRaw()));
            return;
        }
        if (event.isOld()) { // Invisible cloak unequipped
            unequipCloak(event);
        }
        else { // Invisible cloak equipped
            equipCloak(event);
        }
    }

    @PancakeItemEventHandler(source = PancakeItemSource.CHESTPLATE)
    public void armorEquipOther(ArmorEquipItemEvent event) {
        if (event.getBukkitItemStack() == null) return;
        if (event.getItemStack() != null && event.getItemStack().isId(id)) return;
        if ((event.getBukkitNewItemStack() != null && event.getBukkitOldItemStack() != null) ||
                isSomeArmorEquipped(event.getBukkit().getPlayer().getInventory())) return; // Player was notified about cloak
        if (event.getBukkitNewItemStack() != null) { // disable cloak
            event.getBukkit().getPlayer().sendActionBar(Component.text(getLoseEffectCloakMessage().getRaw()));
            disableCloak(event.getBukkit().getPlayer());
        }
        else {
            event.getBukkit().getPlayer().sendActionBar(Component.text(getEnableEffectCloakMessage().getRaw()));
            enableCloak(event.getBukkit().getPlayer());
        }

    }

    protected void equipCloak(ArmorEquipItemEvent event) {
        PancakeItemStack cloak = event.getItemStack();
        Player player = event.getBukkit().getPlayer();
        player.sendActionBar(Component.text(getEquipCloakMessage().getRaw()));
        enableCloak(player);
    }

    protected void unequipCloak(ArmorEquipItemEvent event) {
        PancakeItemStack cloak = event.getItemStack();
        Player player = event.getBukkit().getPlayer();
        player.sendActionBar(Component.text(getUnequipCloakMessage().getRaw()));
        disableCloak(player);
    }

    protected void enableCloak(Player player) {
        player.addPotionEffect(getPotionEffect());
        getEntityIds().add(player.getEntityId());
    }

    protected void disableCloak(Player player) {
        player.removePotionEffect(getPotionEffect().getType());
        getEntityIds().remove((Object) player.getEntityId());
        sendPacketSlotSet(player, player.getInventory().getChestplate());
    }

    protected void sendPacketSlotSet(Player player, ItemStack item) {
        Pancake pancake = Pancake.getPlugin();
        PacketContainer armorSet = new PacketContainer(PacketType.Play.Server.ENTITY_EQUIPMENT);
        armorSet.getIntegers()
                .write(0, player.getEntityId());
        armorSet.getSlotStackPairLists()
                .write(0, Arrays.asList(
                        new Pair<>(EnumWrappers.ItemSlot.MAINHAND, player.getInventory().getItemInMainHand()),
                        new Pair<>(EnumWrappers.ItemSlot.OFFHAND, player.getInventory().getItemInOffHand()),
                        new Pair<>(EnumWrappers.ItemSlot.CHEST, item)
                ));
        try {
            for (Player onlinePlayer: Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.getUniqueId().equals(player.getUniqueId())) continue;
                pancake.getProtocolManager().sendServerPacket(onlinePlayer, armorSet);
            }
        } catch (InvocationTargetException e) {
            pancake.getLogger().log(Level.SEVERE, "Exception:", e);
        }
    }

    protected boolean isSomeArmorEquipped(PlayerInventory inventory) {
        return  !(PancakeItemUtils.isItemNone(inventory.getHelmet()) &&
                PancakeItemUtils.isItemNone(inventory.getLeggings()) &&
                PancakeItemUtils.isItemNone(inventory.getBoots()));
    }

    @Override
    public void load(PancakeData data) {
        setPotionEffect(data.getPotionEffect("effect", getPotionEffect()));
        setEquipCloakMessage(data.getMessage("equipCloakMessage", getEquipCloakMessage()));
        setUnequipCloakMessage(data.getMessage("unequipCloakMessage", getUnequipCloakMessage()));
        setLoseEffectCloakMessage(data.getMessage("loseEffectCloakMessage", getLoseEffectCloakMessage()));
        setEnableEffectCloakMessage(data.getMessage("enableEffectCloakMessage", getEnableEffectCloakMessage()));
    }

    @Override
    public void save(PancakeData data) {
        data.set("effect", getPotionEffect());
        data.set("equipCloakMessage", getEquipCloakMessage());
        data.set("unequipCloakMessage", getUnequipCloakMessage());
        data.set("loseEffectCloakMessage", getLoseEffectCloakMessage());
        data.set("enableEffectCloakMessage", getEnableEffectCloakMessage());
    }
}
