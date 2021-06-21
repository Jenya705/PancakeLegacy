package com.github.jenya705.pancake;

import com.github.jenya705.pancake.item.PancakeItemContainer;
import com.github.jenya705.pancake.item.model.CustomModelArmor;
import com.github.jenya705.pancake.item.model.CustomModelItem;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Register of all resource pack objects
 */
public interface ResourcePackRegister {

    /**
     *
     * Add to resource pack item model
     *
     * @param customModelItem Custom model item
     * @param itemContainer Item container
     * @param plugin Owner of model
     */
    void registerItemModel(CustomModelItem customModelItem, PancakeItemContainer<?> itemContainer, JavaPlugin plugin);

    /**
     *
     * Add to resource pack armor model
     *
     * @param customModelArmor Custom model armor
     * @param itemContainer Item container
     * @param plugin Owner of model
     */
    void registerArmorModel(CustomModelArmor customModelArmor, PancakeItemContainer<?> itemContainer, JavaPlugin plugin);

}
