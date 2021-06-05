package com.github.jenya705.pancake;

import lombok.experimental.UtilityClass;

import java.util.function.Consumer;

@UtilityClass
public class PancakeUtils {

    public static <T> void consumeIfNotNull(T object, Consumer<T> consumer) {
        if (object != null) consumer.accept(object);
    }

}
