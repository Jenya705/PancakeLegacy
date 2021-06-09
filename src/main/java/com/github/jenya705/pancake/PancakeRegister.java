package com.github.jenya705.pancake;

import com.github.jenya705.pancake.item.PancakeItemContainer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public interface PancakeRegister {

    PancakeItemContainer<?> getItemContainer(String id);

    List<String> getItemNames();

    void registerAll(String packageName, JavaPlugin plugin);

}
