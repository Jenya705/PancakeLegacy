package com.github.jenya705.pancake.resourcepack;

import java.util.List;
import java.util.Map;

/**
 * Resource pack model
 */
public interface ResourcePackModel {

    /**
     *
     * Return builder
     *
     * @return builder
     */
    static ResourcePackModelImpl.ResourcePackModelImplBuilder builder() {
        return ResourcePackModelImpl.builder();
    }

    /**
     *
     * Return parent field of model
     *
     * @return Parent field of model
     */
    String getParent();

    /**
     *
     * Return textures field of model
     *
     * @return textures field of model
     */
    Map<String, String> getTextures();

    /**
     *
     * Return overrides field of model
     *
     * @return overrides field of model
     */
    List<ResourcePackModelOverride> getOverrides();

    /**
     *
     * Return object as json map (String, Object)
     *
     * @return Object as json map (String, Object)
     */
    Map<String, Object> asMap();

}
