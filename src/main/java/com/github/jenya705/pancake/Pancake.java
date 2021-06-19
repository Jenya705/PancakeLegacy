package com.github.jenya705.pancake;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.github.jenya705.pancake.command.CommandEnchant;
import com.github.jenya705.pancake.command.CommandGive;
import com.github.jenya705.pancake.data.PancakeDataFactory;
import com.github.jenya705.pancake.data.PancakeDataFactoryImpl;
import com.github.jenya705.pancake.enchantment.PancakeEnchantmentTableListener;
import com.github.jenya705.pancake.enchantment.PancakeEnchantmentWeightContainer;
import com.github.jenya705.pancake.enchantment.PancakeEnchantmentWeightContainerImpl;
import com.github.jenya705.pancake.event.armorequip.ArmorEquipRegisterUtils;
import com.github.jenya705.pancake.item.PancakeBukkitItemListener;
import com.github.jenya705.pancake.item.model.CustomModelDataContainer;
import com.github.jenya705.pancake.item.model.CustomModelDataContainerImpl;
import com.github.jenya705.pancake.nms.PancakeNMS;
import com.github.jenya705.pancake.nms.PancakeNMSImpl;
import com.github.jenya705.pancake.resourcepack.ResourcePack;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Setter(AccessLevel.PROTECTED)
@Getter
public final class Pancake extends JavaPlugin {

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private static Pancake plugin;

    // NMS
    private PancakeNMS nms;

    // Protocol
    private ProtocolManager protocolManager;

    // Factories
    private PancakeDataFactory dataFactory;

    // Containers
    private CustomModelDataContainer customModelDataContainer;
    private PancakeEnchantmentWeightContainer enchantmentWeightContainer;

    // Register
    private PancakeRegister register;
    private ResourcePackRegister resourcePackRegister;

    // Resource pack
    private ResourcePack resourcePack;

    @Override
    @SneakyThrows
    public void onLoad() {
        setPlugin(this);
        setNms(new PancakeNMSImpl());
        setProtocolManager(ProtocolLibrary.getProtocolManager());
        setDataFactory(new PancakeDataFactoryImpl());
        setRegister(new PancakeRegisterImpl());
        setResourcePackRegister(new ResourcePackRegisterImpl());
        setEnchantmentWeightContainer(new PancakeEnchantmentWeightContainerImpl());
        setCustomModelDataContainer(new CustomModelDataContainerImpl());
        setResourcePack(ResourcePack.of(new File(getDataFolder(), "resource_pack")));
    }

    @Override
    public void onEnable() {
        try {
            Class.forName("org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack");
            Class.forName("io.papermc.paper.enchantments.EnchantmentRarity");
        } catch (ClassNotFoundException e) {
            getLogger().info("This version of pancake is only for 1.16.3, 1.16.4 or 1.16.5 version of the newest paper");
            getServer().getPluginManager().disablePlugin(plugin);
        }
        PancakeEnchantmentWeightContainerImpl.initializeBukkitEnchantments(getEnchantmentWeightContainer());
        getResourcePack().meta(6, "Pancake plugin resource pack");
        registerCommand("give", new CommandGive());
        registerCommand("enchant", new CommandEnchant());
        ArmorEquipRegisterUtils.enable();
        getRegister().registerAll("com.github.jenya705.pancake", this);
        getServer().getPluginManager().registerEvents(new PancakeBukkitItemListener(), this);
        getServer().getPluginManager().registerEvents(new PancakeEnchantmentTableListener(), this);
        getDataFolder().mkdir();
    }

    @Override
    public void onDisable() {
        getCustomModelDataContainer().save();
    }

    protected void registerCommand(String command, Object commandExecutor) {
        PluginCommand commandObject = getCommand(command);
        if (commandObject == null) return;
        if (commandExecutor instanceof CommandExecutor) commandObject.setExecutor((CommandExecutor) commandExecutor);
        if (commandExecutor instanceof TabExecutor) commandObject.setTabCompleter((TabExecutor) commandExecutor);
    }

}
