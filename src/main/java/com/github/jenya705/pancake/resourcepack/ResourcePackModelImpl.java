package com.github.jenya705.pancake.resourcepack;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter(AccessLevel.PROTECTED)
@Builder
public class ResourcePackModelImpl implements ResourcePackModel {

    private String parent;
    private Map<String, String> textures;
    private List<ResourcePackModelOverride> overrides;

    public static class ResourcePackModelImplBuilder {

        public ResourcePackModelImplBuilder texture(String layer, String texture) {
            if (textures == null) textures = new HashMap<>();
            textures.put(layer, texture);
            return this;
        }

        public ResourcePackModelImplBuilder override(ResourcePackModelOverride override) {
            if (overrides == null) overrides = new ArrayList<>();
            overrides.add(override);
            return this;
        }
    }

}
