package com.github.jenya705.pancake.item;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PancakeItemEventHandler {

    /**
     *
     * Return id of listening pancake item or empty if default
     *
     * @return id of listening pancake item or empty if default
     */
    String id() default ""; // empty

    /**
     *
     * Return source of item when handler will be invoked
     *
     * @return source of item when handler will be invoked
     */
    PancakeItemSource source() default PancakeItemSource.MAIN;
}
