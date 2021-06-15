package com.github.jenya705.pancake.resourcepack.optifine;

import java.io.File;
import java.util.Properties;

/**
 * Optifine resource pack directory builder
 */
public interface OptifineResourcePack {

    static OptifineResourcePack of(File sourceFolder) {
        return new OptifineResourcePackImpl(sourceFolder);
    }

    /**
     *
     * Creates file
     *
     * @param name File name
     * @param bytes Bytes
     * @return OptifineResourcePack with added file
     */
    OptifineResourcePack add(String name, byte[] bytes);

    /**
     *
     * Creates properties file
     *
     * @param name File name (without .properties)
     * @param properties Properties object
     * @return OptifineResourcePack with added properties file
     */
    OptifineResourcePack properties(String name, Properties properties);

}
