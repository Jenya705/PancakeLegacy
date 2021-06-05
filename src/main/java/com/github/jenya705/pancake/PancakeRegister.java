package com.github.jenya705.pancake;

import com.github.jenya705.pancake.item.PancakeItemContainer;
import org.bukkit.plugin.java.JavaPlugin;

public interface PancakeRegister {

    PancakeItemContainer<?> getItemContainer(String id);

    void registerAll(String packageName, JavaPlugin plugin);

}
