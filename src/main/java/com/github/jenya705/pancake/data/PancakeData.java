package com.github.jenya705.pancake.data;

import com.github.jenya705.pancake.PancakeMessage;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

/**
 * Pancake Data
 */
public interface PancakeData {

    // Exceptionable

    /**
     *
     * Return object with given key
     *
     * @param key Key
     * @return Object with given key
     */
    Object getObject(Object key);

    /**
     *
     * Return boolean with given key
     *
     * @throws IllegalArgumentException if object with given key is not boolean
     * @param key Key
     * @return Boolean with given key
     */
    Boolean getBoolean(Object key);

    /**
     *
     * Return byte with given key
     *
     * @throws IllegalArgumentException if object with given key is not byte
     * @param key Key
     * @return Byte with given key
     */
    Byte getByte(Object key);

    /**
     *
     * Return short with given key
     *
     * @throws IllegalArgumentException if object with given key is not short
     * @param key Key
     * @return Short with given key
     */
    Short getShort(Object key);

    /**
     *
     * Return integer with given key
     *
     * @throws IllegalArgumentException if object with given key is not integer
     * @param key Key
     * @return Integer with given key
     */
    Integer getInteger(Object key);

    /**
     *
     * Return long with given key
     *
     * @throws IllegalArgumentException if object with given key is not long
     * @param key Key
     * @return Long with given key
     */
    Long getLong(Object key);

    /**
     *
     * Return string with given key
     *
     * @throws IllegalArgumentException if object with given key is not string
     * @param key Key
     * @return String with given key
     */
    String getString(Object key);

    /**
     *
     * Return float with given key
     *
     * @throws IllegalArgumentException if object with given key is not float
     * @param key Key
     * @return Float with given key
     */
    Float getFloat(Object key);

    /**
     *
     * Return double with given key
     *
     * @throws IllegalArgumentException if object with given key is not double
     * @param key Key
     * @return Double with given key
     */
    Double getDouble(Object key);

    /**
     *
     * Return list with given key
     *
     * @throws IllegalArgumentException if object with given key is not list
     * @param key Key
     * @return List with given key
     */
    List<Object> getArray(Object key);

    /**
     *
     * Return directory as {@link PancakeData} with given key
     *
     * @throws IllegalArgumentException if object with given key is not directory
     * @param key Key
     * @return Directory as {@link PancakeData} with given key
     */
    PancakeData getDirectory(Object key);

    /**
     *
     * Return potion effect with given key
     *
     * @throws IllegalArgumentException if object with given key is not potion effect
     * @param key Key
     * @return Potion effect with given key
     */
    PotionEffect getPotionEffect(Object key);

    /**
     *
     * Return potion effect type with given key
     *
     * @throws IllegalArgumentException if object with given key is not potion effect type
     * @param key Key
     * @return Potion effect type with given key
     */
    PotionEffectType getPotionEffectType(Object key);

    /**
     *
     * Return message with given key
     *
     * @throws IllegalArgumentException if object with given key is not message
     * @param key Key
     * @return Message with given key
     */
    PancakeMessage getMessage(Object key);

    // Defaults

    /**
     *
     * Return object with given key otherwise default value
     *
     * @param key Key
     * @param defaultValue Default value
     * @return Object with given key otherwise default value
     */
    Object getObject(Object key, Object defaultValue);

    /**
     *
     * Return boolean with given key otherwise default value
     *
     * @param key Key
     * @param defaultValue Default value
     * @return Boolean with given key otherwise default value
     */
    Boolean getBoolean(Object key, Boolean defaultValue);

    /**
     *
     * Return byte with given key otherwise default value
     *
     * @param key Key
     * @param defaultValue Default value
     * @return Byte with given key otherwise default value
     */
    Byte getByte(Object key, Byte defaultValue);

    /**
     *
     * Return short with given key otherwise default value
     *
     * @param key Key
     * @param defaultValue Default value
     * @return Short with given key otherwise default value
     */
    Short getShort(Object key, Short defaultValue);

    /**
     *
     * Return integer with given key otherwise default value
     *
     * @param key Key
     * @param defaultValue Default value
     * @return Integer with given key otherwise default value
     */
    Integer getInteger(Object key, Integer defaultValue);


    /**
     *
     * Return long with given key otherwise default value
     *
     * @param key Key
     * @param defaultValue Default value
     * @return Long with given key otherwise default value
     */
    Long getLong(Object key, Long defaultValue);

    /**
     *
     * Return string with given key otherwise default value
     *
     * @param key Key
     * @param defaultValue Default value
     * @return String with given key otherwise default value
     */
    String getString(Object key, String defaultValue);

    /**
     *
     * Return float with given key otherwise default value
     *
     * @param key Key
     * @param defaultValue Default value
     * @return Float with given key otherwise default value
     */
    Float getFloat(Object key, Float defaultValue);

    /**
     *
     * Return double with given key otherwise default value
     *
     * @param key Key
     * @param defaultValue Default value
     * @return Double with given key otherwise default value
     */
    Double getDouble(Object key, Double defaultValue);

    /**
     *
     * Return list with given key otherwise default value
     *
     * @param key Key
     * @param defaultValue Default value
     * @return List with given key otherwise default value
     */
    List<Object> getArray(Object key, List<Object> defaultValue);

    /**
     *
     * Return directory as {@link PancakeData} with given key otherwise default value
     *
     * @param key Key
     * @param defaultValue Default value
     * @return Directory as {@link PancakeData} with given key otherwise default value
     */
    PancakeData getDirectory(Object key, PancakeData defaultValue);

    /**
     *
     * Return potion effect with given key otherwise default value
     *
     * @param key Key
     * @param defaultValue Default value
     * @return Potion effect with given key otherwise default value
     */
    PotionEffect getPotionEffect(Object key, PotionEffect defaultValue);

    /**
     *
     * Return potion effect type with given key otherwise default value
     *
     * @param key Key
     * @param defaultValue Default value
     * @return Potion effect type with given key otherwise default value
     */
    PotionEffectType getPotionEffectType(Object key, PotionEffectType defaultValue);

    /**
     *
     * Return message with given key otherwise default value
     *
     * @param key Key
     * @param defaultValue Default value
     * @return Message with given key otherwise default value
     */
    PancakeMessage getMessage(Object key, PancakeMessage defaultValue);

    /**
     *
     * Add value with key in data
     *
     * @param key Key
     * @param value Value
     */
    void set(Object key, Object value);

    /**
     *
     * Check if object with key in data
     *
     * @param key Key
     * @return Is object with key in data
     */
    boolean hasKey(Object key);

    /**
     *
     * Returns bytes representation of data
     *
     * @return Bytes representation of data
     */
    byte[] toBytes();

    /**
     *
     * Return string represantation of data
     *
     * @throws UnsupportedOperationException if data can not be string
     * @return String representation of data
     */
    String toDeepString();

}
