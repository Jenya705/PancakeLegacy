package com.github.jenya705.pancake.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class PancakeSerializedMapData<T, V> implements PancakeSerializedData {

    private Map<T, V> data;

    @Override
    @SuppressWarnings("Unchecked")
    public <K> K get(Object key) {
        return (K) data.get(key);
    }

    @Override
    @SuppressWarnings("Unchecked")
    public <K> K get(Object key, K defaultValue) {
        return (K) data.getOrDefault(key, (V) defaultValue);
    }

    @Override
    @SuppressWarnings("Unchecked")
    public void set(Object key, Object value) {
        data.put((T) key, (V) value);
    }

    @Override
    public boolean hasKey(Object key) {
        return data.containsKey(key);
    }

}
