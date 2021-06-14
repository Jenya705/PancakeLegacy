package com.github.jenya705.pancake.item.model;

import com.github.jenya705.pancake.resourcepack.ResourcePackModelImpl;
import com.github.jenya705.pancake.resourcepack.ResourcePackModelOverride;

public interface CustomModelItem {

    /**
     * @return model name
     */
    String getModelName();

    /**
     *
     * Apply Custom model (by default just write by default custom_model_data)
     *
     * @param builder Resource pack model builder
     * @param customModelData Custom model data of this item
     */
    default void apply(ResourcePackModelImpl.ResourcePackModelImplBuilder builder, int customModelData) {
        builder.override(ResourcePackModelOverride.builder()
                .predicate("custom_model_data", customModelData)
                .model("item/" + getModelName())
                .build()
        );
    }

}
