package com.github.jenya705.pancake.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.function.Consumer;

@UtilityClass
public class PancakeUtils {

    public static <T> void consumeIfNotNull(T object, Consumer<T> consumer) {
        if (object != null) consumer.accept(object);
    }

    @SneakyThrows
    public static String getFullFieldInformation(Object obj) {
        StringBuilder builder = new StringBuilder();
        for (Field field: obj.getClass().getDeclaredFields()) {
            boolean wasAccessible;
            if (wasAccessible = !field.canAccess(obj)) field.setAccessible(true);
            builder.append(field.getName()).append(": ").append(field.getType()).append(" = ").append(field.get(obj)).append("\n");
            if (wasAccessible) field.setAccessible(false);
        }
        return builder.toString();
    }

    public static <T> boolean contains(T[] array, T object) {
        for (T objectInArray: array) if (objectInArray.equals(object)) return true;
        return false;
    }

}
