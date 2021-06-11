package com.github.jenya705.pancake;

import com.github.jenya705.pancake.enchantment.PancakeEnchantmentContainer;
import com.github.jenya705.pancake.item.PancakeItemContainer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public interface PancakeRegister {

    PancakeItemContainer<?> getItemContainer(String id);

    PancakeEnchantmentContainer<?> getEnchantContainer(String id);

    List<String> getItemNames();

    List<String> getEnchantmentNames();

    void registerAll(String packageName, JavaPlugin plugin);

}
