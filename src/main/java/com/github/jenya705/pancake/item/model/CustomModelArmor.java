package com.github.jenya705.pancake.item.model;

import com.github.jenya705.pancake.item.PancakeItemContainer;
import com.github.jenya705.pancake.resourcepack.optifine.OptifineResourcePack;
import com.github.jenya705.pancake.util.PropertiesBuilder;

import java.util.Locale;

public interface CustomModelArmor {

    /**
     * @return Model name
     */
    String getModelName();

    /**
     *
     * Apply model armor
     *
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
                        .property("texture", getModelName())
                        .property("nbt.CustomModelData", Integer.toString(customModelData))
                        .build()
        );
    }

}
