package com.github.jenya705.pancake.item;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PancakeItemEventHandler {

    /**
     * @return id of listening pancake item or empty String if default
     */
    String id() default ""; // empty

    /**
     * @return if object is from this source, event handler will be invoked
     */
    PancakeItemSource source() default PancakeItemSource.MAIN;
}
