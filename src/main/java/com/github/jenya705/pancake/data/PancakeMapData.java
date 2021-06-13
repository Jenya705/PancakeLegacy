package com.github.jenya705.pancake.data;

import com.github.jenya705.pancake.PancakeMessage;
import com.github.jenya705.pancake.PancakeMessageImpl;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class PancakeMapData<T, V> implements PancakeData {

    public static final Map<Class<?>, Function<Object, Object>> parsers = new HashMap<>();
    static {
        parsers.put(PotionEffectType.class, (obj) -> PancakeMapData.getNativePotionEffectType((PotionEffectType) obj));
        parsers.put(PotionEffect.class, (obj) -> PancakeMapData.getNativePotionEffect((PotionEffect) obj));
        parsers.put(PancakeMessageImpl.class, (obj) -> ((PancakeMessage) obj).getLocal());
    }

    private Map<T, V> data;

    // Exceptionable

    @Override
    public Object getObject(Object key) {
        return data.get(key);
    }

    @Override
    public Boolean getBoolean(Object key) {
        Object obj = data.get(key);
        if (obj instanceof Boolean) return (Boolean) obj;
        throw new IllegalArgumentException(String.format("Object: %s is not exact type of executed method", obj));
    }

    @Override
    public Byte getByte(Object key) {
        Object obj = data.get(key);
        if (obj instanceof Byte) return (Byte) obj;
        throw new IllegalArgumentException(String.format("Object: %s is not exact type of executed method", obj));
    }

    @Override
    public Short getShort(Object key) {
        Object obj = data.get(key);
        if (obj instanceof Short) return (Short) obj;
        throw new IllegalArgumentException(String.format("Object: %s is not exact type of executed method", obj));
    }

    @Override
    public Integer getInteger(Object key) {
        Object obj = data.get(key);
        if (obj instanceof Integer) return (Integer) obj;
        throw new IllegalArgumentException(String.format("Object: %s is not exact type of executed method", obj));
    }

    @Override
    public Long getLong(Object key) {
        Object obj = data.get(key);
        if (obj instanceof Long) return (Long) obj;
        throw new IllegalArgumentException(String.format("Object: %s is not exact type of executed method", obj));
    }

    @Override
    public String getString(Object key) {
        Object obj = data.get(key);
        if (obj instanceof String) return (String) obj;
        throw new IllegalArgumentException(String.format("Object: %s is not exact type of executed method", obj));
    }

    @Override
    public Float getFloat(Object key) {
        Object obj = data.get(key);
        if (obj instanceof Float) return (Float) obj;
        throw new IllegalArgumentException(String.format("Object: %s is not exact type of executed method", obj));
    }

    @Override
    public Double getDouble(Object key) {
        Object obj = data.get(key);
        if (obj instanceof Double) return (Double) obj;
        throw new IllegalArgumentException(String.format("Object: %s is not exact type of executed method", obj));
    }

    @Override
    public List<Object> getArray(Object key) {
        Object obj = data.get(key);
        if (obj instanceof List) return (List<Object>) obj;
        throw new IllegalArgumentException(String.format("Object: %s is not exact type of executed method", obj));
    }

    @Override
    public PancakeData getDirectory(Object key) {
        Object obj = data.get(key);
        if (obj instanceof Map) {
            PancakeMapData<T, V> mapData = createSelf();
            mapData.setData((Map<T, V>) obj);
            return mapData;
        }
        throw new IllegalArgumentException(String.format("Object: %s is not exact type of executed method", obj));
    }

    @Override
    public PotionEffect getPotionEffect(Object key) {
        Object obj = data.get(key);
        if (obj instanceof PotionEffect) return (PotionEffect) obj;
        PancakeData data;
        if (obj instanceof Map) {
            data = getDirectory(key);
        }
        else if (obj instanceof PancakeData) {
            data = (PancakeData) obj;
        }
        else {
            throw new IllegalArgumentException(String.format("Object: %s is not exact type of executed method", obj));
        }
        return new PotionEffect(
                data.getPotionEffectType("effect"),
                data.getInteger("duration"),
                data.getInteger("amplifier"),
                data.getBoolean("ambient", true),
                data.getBoolean("has-particles", true),
                data.getBoolean("has-icon", true)
        );
    }

    @Override
    public PotionEffectType getPotionEffectType(Object key) {
        Object obj = data.get(key);
        if (obj instanceof PotionEffectType) {
            return (PotionEffectType) obj;
        }
        else if (obj instanceof String) {
            return PotionEffectType.getByName((String) obj);
        }
        else if (obj instanceof Integer) {
            return PotionEffectType.values()[(int) obj];
        }
        throw new IllegalArgumentException(String.format("Object: %s is not exact type of executed method", obj));
    }

    @Override
    public PancakeMessage getMessage(Object key) {
        Object obj = data.get(key);
        if (obj instanceof String) return PancakeMessage.of((String) obj);
        else if (obj instanceof PancakeMessage) return (PancakeMessage) obj;
        throw new IllegalArgumentException(String.format("Object: %s is not exact type of executed method", obj));
    }

    // Defaults

    @Override
    public Object getObject(Object key, Object defaultValue) {
        if (!hasKey(key)) return defaultValue;
        return getObject(key);
    }

    @Override
    public Boolean getBoolean(Object key, Boolean defaultValue) {
        if (!hasKey(key)) return defaultValue;
        return getBoolean(key);
    }

    @Override
    public Byte getByte(Object key, Byte defaultValue) {
        if (!hasKey(key)) return defaultValue;
        return getByte(key);
    }

    @Override
    public Short getShort(Object key, Short defaultValue) {
        if (!hasKey(key)) return defaultValue;
        return getShort(key);
    }

    @Override
    public Integer getInteger(Object key, Integer defaultValue) {
        if (!hasKey(key)) return defaultValue;
        return getInteger(key);
    }

    @Override
    public Long getLong(Object key, Long defaultValue) {
        if (!hasKey(key)) return defaultValue;
        return getLong(key);
    }

    @Override
    public String getString(Object key, String defaultValue) {
        if (!hasKey(key)) return defaultValue;
        return getString(key);
    }

    @Override
    public Float getFloat(Object key, Float defaultValue) {
        if (!hasKey(key)) return defaultValue;
        return getFloat(key);
    }

    @Override
    public Double getDouble(Object key, Double defaultValue) {
        if (!hasKey(key)) return defaultValue;
        return getDouble(key);
    }

    @Override
    public List<Object> getArray(Object key, List<Object> defaultValue) {
        if (!hasKey(key)) return defaultValue;
        return getArray(key);
    }

    @Override
    public PancakeData getDirectory(Object key, PancakeData defaultValue) {
        if (!hasKey(key)) return defaultValue;
        return getDirectory(key);
    }

    @Override
    public PotionEffect getPotionEffect(Object key, PotionEffect defaultValue) {
        if (!hasKey(key)) return defaultValue;
        return getPotionEffect(key);
    }

    @Override
    public PotionEffectType getPotionEffectType(Object key, PotionEffectType defaultValue) {
        if (!hasKey(key)) return defaultValue;
        return getPotionEffectType(key);
    }

    @Override
    public PancakeMessage getMessage(Object key, PancakeMessage defaultValue) {
        if (!hasKey(key)) return defaultValue;
        return getMessage(key);
    }

    @Override
    @SuppressWarnings("Unchecked")
    public void set(Object key, Object value) {
        data.put((T) key, (V) value);
    }

    public PancakeMapData<T, V> add(Object key, Object value) {
        set(key, value);
        return this;
    }

    public PancakeMapData<T, V> add(Object key, Object value, boolean condition) {
        if (condition) set(key, value);
        return this;
    }

    @Override
    public boolean hasKey(Object key) {
        return data.containsKey(key);
    }

    public Map<T, V> toNativeMap() {
        Map<T, V> result = new HashMap<>();
        for (Map.Entry<T, V> entry : getData().entrySet()) {
            if (entry.getValue() instanceof PancakeMapData) result.put(entry.getKey(), (V) ((PancakeMapData<?, ?>) entry.getValue()).toNativeMap());
            Function<Object, Object> parseFunction = parsers.getOrDefault(entry.getValue().getClass(), null);
            if (parseFunction != null) {
                result.put(entry.getKey(), (V) parseFunction.apply(entry.getValue()));
            }
            else {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    public abstract PancakeMapData<T, V> createSelf();

    // Parse Functions

    public static Map<String, Object> getNativePotionEffect(PotionEffect potionEffect) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("effect", getNativePotionEffectType(potionEffect.getType()));
        result.put("duration", potionEffect.getDuration());
        result.put("amplifier", potionEffect.getAmplifier());
        result.put("ambient", potionEffect.isAmbient());
        result.put("has-particles", potionEffect.hasParticles());
        result.put("has-icon", potionEffect.hasIcon());
        return result;
    }

    public static String getNativePotionEffectType(PotionEffectType potionEffectType) {
        return potionEffectType.getName();
    }


}
