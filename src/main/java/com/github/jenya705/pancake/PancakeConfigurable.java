package com.github.jenya705.pancake;

import com.github.jenya705.pancake.data.PancakeSerializedData;

public interface PancakeConfigurable {

    /**
     * @param data Data of Pancake object
     */
    void load(PancakeSerializedData data);

    /**
     * @param data Data of Pancake Object
     */
    void save(PancakeSerializedData data);

}
