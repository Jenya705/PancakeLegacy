package com.github.jenya705.pancake.resourcepack;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter(AccessLevel.PROTECTED)
@Builder
public class ResourcePackModelOverrideImpl implements ResourcePackModelOverride {

    private String model;
    private Map<String, Object> predicate;

    public static class ResourcePackModelOverrideImplBuilder {

        public ResourcePackModelOverrideImplBuilder predicate(String key, Object value) {
            if (predicate == null) predicate = new HashMap<>();
            predicate.put(key, value);
            return this;
        }

    }

}
