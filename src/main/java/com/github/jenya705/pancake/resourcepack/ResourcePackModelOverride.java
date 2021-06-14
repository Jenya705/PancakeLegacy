package com.github.jenya705.pancake.resourcepack;

import java.util.Map;

public interface ResourcePackModelOverride {

    static ResourcePackModelOverrideImpl.ResourcePackModelOverrideImplBuilder builder() {
        return ResourcePackModelOverrideImpl.builder();
    }

    String getModel();

    Map<String, Object> getPredicate();

}
