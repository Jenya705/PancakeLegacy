package com.github.jenya705.pancake.item.model;

import com.github.jenya705.pancake.item.container.PancakeItemContainer;
import com.github.jenya705.pancake.resourcepack.ResourcePackModel;
import com.github.jenya705.pancake.resourcepack.ResourcePackModelOverride;

/**
 * Interface to generate item model
 */
public interface CustomModelItem {

    /**
     *
     * Return model name
     *
     * @return model name
     */
    String getModelName();

    /**
     *
     * Checks if pancake need to automatically load textures
     *
     * @return is automatically loading textures from plugin
     */
    default boolean isLoadItemTextures() {
        return true;
    }

    /**
     *
     * Apply Custom model (by default just write by default custom_model_data)
     *
     * @implSpec generates predicate with "custom_model_data" tag and model "item/{@link #getModelName()}"
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
