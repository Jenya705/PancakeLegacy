package com.github.jenya705.pancake.resourcepack;

import java.util.List;
import java.util.Map;

/**
 * Resource pack model
 */
public interface ResourcePackModel {

    static ResourcePackModelImpl.ResourcePackModelImplBuilder builder() {
        return ResourcePackModelImpl.builder();
    }

    String getParent();

    Map<String, String> getTextures();

    List<ResourcePackModelOverride> getOverrides();

}
