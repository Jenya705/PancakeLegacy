package com.github.jenya705.pancake.data;

/**
 * Pancake Data
 */
public interface PancakeSerializedData {

    <T> T get(Object key);

    <T> T get(Object key, T defaultValue);

    void set(Object key, Object value);

    boolean hasKey(Object key);

    byte[] toBytes();

    /**
     * @throws UnsupportedOperationException if can not be String
     * @return String
     */
    String toDeepString();

}
