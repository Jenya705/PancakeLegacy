package com.github.jenya705.pancake;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.github.jenya705.pancake.command.CommandEnchant;
import com.github.jenya705.pancake.command.CommandGive;
import com.github.jenya705.pancake.data.PancakeDataFactory;
import com.github.jenya705.pancake.data.PancakeDataFactoryImpl;
import com.github.jenya705.pancake.event.armorequip.ArmorEquipRegisterUtils;
import com.github.jenya705.pancake.item.PancakeBukkitItemListener;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

@Setter(AccessLevel.PROTECTED)
@Getter
public final class Pancake extends JavaPlugin {

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private static Pancake plugin;

    // Protocol
    private ProtocolManager protocolManager;

    // Factories
    private PancakeDataFactory dataFactory;

    // Register
    private PancakeRegister register;

    @Override
    public void onLoad() {
        setPlugin(this);
        setProtocolManager(ProtocolLibrary.getProtocolManager());
        setDataFactory(new PancakeDataFactoryImpl());
        setRegister(new PancakeRegisterImpl());
    }

    @Override
    public void onEnable() {
        registerCommand("give", new CommandGive());
        registerCommand("enchant", new CommandEnchant());
        ArmorEquipRegisterUtils.enable();
        getRegister().registerAll("com.github.jenya705.pancake", this);
        getServer().getPluginManager().registerEvents(new PancakeBukkitItemListener(), this);
        getDataFolder().mkdir();
    }

    @Override
    public void onDisable() {

    }

    protected void registerCommand(String command, Object commandExecutor) {
        PluginCommand commandObject = getCommand(command);
        if (commandObject == null) return;
        if (commandExecutor instanceof CommandExecutor) commandObject.setExecutor((CommandExecutor) commandExecutor);
        if (commandExecutor instanceof TabExecutor) commandObject.setTabCompleter((TabExecutor) commandExecutor);
    }

}
