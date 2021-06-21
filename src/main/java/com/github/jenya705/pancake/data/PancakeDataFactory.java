package com.github.jenya705.pancake.data;

import java.io.File;

/**
 * Interface with methods to create {@link PancakeData} objects
 */
public interface PancakeDataFactory {

    /**
     *
     * Return loaded from content data with given type
     *
     * @param content Content
     * @param type Type
     * @return Loaded from content data with given type
     */
    PancakeData load(String content, PancakeDataType type);

    /**
     *
     * Return loaded from file data with given type
     *
     * @param file File
     * @param type Type
     * @return Loaded from file data with given type
     */
    PancakeData load(File file, PancakeDataType type);

    /**
     *
     * Return loaded from file data, type chooses from file extension
     *
     * @param file File
     * @return Loaded from file data
     */
    PancakeData load(File file);

    /**
     *
     * Return created empty data with given type
     *
     * @param type Type
     * @return Created empty data with given type
     */
    PancakeData create(PancakeDataType type);

}
