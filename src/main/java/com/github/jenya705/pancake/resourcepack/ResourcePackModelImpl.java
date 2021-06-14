package com.github.jenya705.pancake.resourcepack;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter(AccessLevel.PROTECTED)
@Builder
public class ResourcePackModelImpl implements ResourcePackModel {

    private String parent;
    private Map<String, String> textures;
    private List<ResourcePackModelOverride> overrides;
    private Map<String, Object> notListed;

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> result;
        if (getNotListed() != null) result = new HashMap<>(getNotListed());
        else result = new HashMap<>();
        if (getParent() != null) result.put("parent", getParent());
        if (getTextures() != null) result.put("textures", getTextures());
        if (getOverrides() != null) result.put("overrides", getOverrides().stream().map(ResourcePackModelOverride::asMap).collect(Collectors.toList()));
        return result;
    }

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

        public ResourcePackModelImplBuilder notListed(String key, Object value) {
            if (notListed == null) notListed = new HashMap<>();
            notListed.put(key, value);
            return this;
        }

    }

}
