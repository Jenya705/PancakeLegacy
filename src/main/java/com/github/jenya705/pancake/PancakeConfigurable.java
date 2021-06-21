package com.github.jenya705.pancake;

import com.github.jenya705.pancake.data.PancakeData;

/**
 * Interface which says that object is configurable
 */
public interface PancakeConfigurable {

    /**
     *
     * Invoking to load variables from data
     *
     * @param data Data of Pancake object
     */
    void load(PancakeData data);

    /**
     *
     * Invoking to save variables to <bold>empty</bold> data
     *
     * @param data Data of Pancake Object
     */
    void save(PancakeData data);

}
