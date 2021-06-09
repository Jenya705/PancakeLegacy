package com.github.jenya705.pancake;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.github.jenya705.pancake.command.CommandGive;
import com.github.jenya705.pancake.data.PancakeDataFactory;
import com.github.jenya705.pancake.data.PancakeDataFactoryImpl;
import com.github.jenya705.pancake.event.armorequip.ArmorEquipMain;
import com.github.jenya705.pancake.item.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
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
        CommandGive give = new CommandGive();
        getCommand("give").setExecutor(give);
        getCommand("give").setTabCompleter(give);
        ArmorEquipMain.enable();
        getRegister().registerAll("com.github.jenya705.pancake", this);
        getServer().getPluginManager().registerEvents(new PancakeBukkitItemListener(), this);
        getDataFolder().mkdir();
    }

    @Override
    public void onDisable() {

    }

}
