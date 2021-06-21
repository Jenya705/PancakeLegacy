package com.github.jenya705.pancake.item.model;

import com.github.jenya705.pancake.item.PancakeItem;
import com.github.jenya705.pancake.item.container.PancakeItemContainer;

/**
 * Container of custom model data for the item
 *
 */
public interface CustomModelDataContainer {

    /**
     *
     * Return custom model data for this item if does not have any generating and return it
     *
     * @param source Source object
     * @param annotation Annotation of object
     * @throws IllegalArgumentException if item is not instance of {@link CustomModelItem}
     * @return custom model data of given pancake item
     */
    int getCustomModelData(Object source, PancakeItem annotation);

    /**
     *
     * Return custom model data for this item if dies not have any generating and return it
     *
     * @param itemContainer Container of item
     * @throws IllegalArgumentException if item container source is not instance of {@link CustomModelItem}
     * @return Custom model data of given container
     */
    int getCustomModelData(PancakeItemContainer<?> itemContainer);

    /**
     * Save all data
     */
    void save();

}
