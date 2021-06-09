package com.github.jenya705.pancake;

import com.github.jenya705.pancake.data.PancakeData;

public interface PancakeConfigurable {

    /**
     * @param data Data of Pancake object
     */
    void load(PancakeData data);

    /**
     * @param data Data of Pancake Object
     */
    void save(PancakeData data);

}
