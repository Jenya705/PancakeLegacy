package com.github.jenya705.pancake.item.model;

import com.github.jenya705.pancake.item.PancakeItemContainer;
import com.github.jenya705.pancake.resourcepack.ResourcePackModel;
import com.github.jenya705.pancake.resourcepack.ResourcePackModelOverride;

public interface CustomModelItem {

    /**
     * @return model name
     */
    String getModelName();

    /**
     * @return is automatically loading textures from plugin
     */
    default boolean isLoadItemTextures() {
        return true;
    }

    /**
     *
     * Apply Custom model (by default just write by default custom_model_data)
     *
     * @param builder Resource pack model builder
     * @param itemContainer Container of this item
     * @param customModelData Custom model data of this item
     */
    default void apply(ResourcePackModel builder, PancakeItemContainer<?> itemContainer, int customModelData) {
        builder.getOverrides().add(ResourcePackModelOverride.builder()
                .predicate("custom_model_data", customModelData)
                .model("item/" + getModelName())
                .build()
        );
    }

}
