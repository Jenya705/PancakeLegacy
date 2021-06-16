package com.github.jenya705.pancake.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

/**
 * Builder for properties
 */
@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public class PropertiesBuilder {

    private Properties properties;

    public static PropertiesBuilder of() {
        return new PropertiesBuilder();
    }

    public Properties build() {
        return properties;
    }

    public PropertiesBuilder property(boolean condition, String key, String value) {
        if (condition) return property(key, value);
        return this;
    }

    public PropertiesBuilder properties(Properties properties) {
        setProperties(properties);
        return this;
    }

    public PropertiesBuilder property(String key, String value) {
        if (getProperties() == null) setProperties(new Properties());
        getProperties().put(key, value);
        return this;
    }

}
