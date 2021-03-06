package com.github.jenya705.pancake.item.model;

import com.github.jenya705.pancake.item.container.PancakeItemContainer;
import com.github.jenya705.pancake.resourcepack.optifine.OptifineResourcePack;
import com.github.jenya705.pancake.util.PropertiesBuilder;

import java.util.Locale;

/**
 * Interface to generate armor model
 */
public interface CustomModelArmor {

    /**
     *
     * Return model name
     *
     * @return Model name
     */
    String getModelName();

    /**
     *
     * Return armor material
     *
     * @return Armor material (e.g. diamond, iron and etc.)
     */
    String getArmorMaterial();

    /**
     *
     * Checks if layer1 included
     *
     * @return is layer 1 included in .properties file
     */
    boolean isLayer1();

    /**
     *
     * Checks if layer2 included
     *
     * @return is layer 2 include in .properties file
     */
    boolean isLayer2();

    /**
     *
     * Checks if pancake need to automatically load all textures
     *
     * @return is automatically loading textures from plugin
     */
    default boolean isLoadArmorTextures() {
        return true;
    }

    /**
     *
     * Apply model armor
     *
     * @implSpec creating .properties file with name {@link #getModelName()} with type, items, layer1, layer2, nbt.CustomModelData
     * @param resourcePack Optifine resource pack
     * @param itemContainer Container of this item
     * @param customModelData custom model data of this item
     */
    default void apply(OptifineResourcePack resourcePack, PancakeItemContainer<?> itemContainer, int customModelData) {
        resourcePack.properties(
                getModelName(),
                PropertiesBuilder.of()
                        .property("type", "armor")
                        .property("items", itemContainer.getMaterial().name().toLowerCase(Locale.ROOT))
                        .property(isLayer1(), "texture." + getArmorMaterial() + "_layer_1", getModelName() + "_layer_1")
                        .property(isLayer2(), "texture." + getArmorMaterial() + "_layer_2", getModelName() + "_layer_2")
                        .property("nbt.CustomModelData", Integer.toString(customModelData))
                        .build()
        );
    }

}
