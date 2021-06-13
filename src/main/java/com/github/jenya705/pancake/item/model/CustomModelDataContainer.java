package com.github.jenya705.pancake.item.model;

import com.github.jenya705.pancake.item.PancakeItem;

public interface CustomModelDataContainer {

    /**
     * @param source Source object
     * @param annotation Annotation of object
     * @throws IllegalArgumentException if item is not instance of {@link CustomModelItem}
     * @return custom model data of given pancake item
     */
    int getCustomModelData(Object source, PancakeItem annotation);

    /**
     * Save all data
     */
    void save();

}
