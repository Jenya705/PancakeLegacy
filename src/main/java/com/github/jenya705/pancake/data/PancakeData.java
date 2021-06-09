package com.github.jenya705.pancake.data;

import com.github.jenya705.pancake.PancakeMessage;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Pancake Data
 */
public interface PancakeData {

    // Exceptionable

    Object getObject(Object key);

    Boolean getBoolean(Object key);

    Byte getByte(Object key);

    Short getShort(Object key);

    Integer getInteger(Object key);

    Long getLong(Object key);

    String getString(Object key);

    Float getFloat(Object key);

    Double getDouble(Object key);

    PancakeData getDirectory(Object key);

    PotionEffect getPotionEffect(Object key);

    PotionEffectType getPotionEffectType(Object key);

    PancakeMessage getMessage(Object key);

    // Defaults

    Object getObject(Object key, Object defaultValue);

    Boolean getBoolean(Object key, Boolean defaultValue);

    Byte getByte(Object key, Byte defaultValue);

    Short getShort(Object key, Short defaultValue);

    Integer getInteger(Object key, Integer defaultValue);

    Long getLong(Object key, Long defaultValue);

    String getString(Object key, String defaultValue);

    Float getFloat(Object key, Float defaultValue);

    Double getDouble(Object key, Double defaultValue);

    PancakeData getDirectory(Object key, PancakeData defaultValue);

    PotionEffect getPotionEffect(Object key, PotionEffect defaultValue);

    PotionEffectType getPotionEffectType(Object key, PotionEffectType defaultValue);

    PancakeMessage getMessage(Object key, PancakeMessage defaultValue);

    void set(Object key, Object value);

    boolean hasKey(Object key);

    byte[] toBytes();

    /**
     * @throws UnsupportedOperationException if can not be String
     * @return String
     */
    String toDeepString();

}
