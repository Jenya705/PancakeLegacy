package com.github.jenya705.pancake.event.armorequip;

import com.github.jenya705.pancake.Pancake;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

/**
 * @author Arnah
 * @since Jul 30, 2015
 */
@UtilityClass
public class ArmorEquipRegisterUtils {

	public static void enable(){
		Bukkit.getServer().getPluginManager().registerEvents(new ArmorListener(), Pancake.getPlugin());
		Bukkit.getServer().getPluginManager().registerEvents(new DispenserArmorListener(), Pancake.getPlugin());
	}

}