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
    private Map<String, Object> notListed;

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> result;
        if (getNotListed() != null) result = new HashMap<>(getNotListed()); // Clone
        else result = new HashMap<>();
        if (getPredicate() != null) result.put("predicate", getPredicate());
        if (getModel() != null) result.put("model", getModel());
        return result;
    }

    public static class ResourcePackModelOverrideImplBuilder {

        public ResourcePackModelOverrideImplBuilder predicate(String key, Object value) {
            if (predicate == null) predicate = new HashMap<>();
            predicate.put(key, value);
            return this;
        }

        public ResourcePackModelOverrideImplBuilder notListed(String key, Object value) {
            if (notListed == null) notListed = new HashMap<>();
            notListed.put(key, value);
            return this;
        }

    }

}
