package com.github.jenya705.pancake.data;

import java.io.File;

public interface PancakeDataFactory {

    PancakeData load(String content, PancakeDataType type);

    PancakeData load(File file, PancakeDataType type);

    PancakeData load(File file);

    PancakeData create(PancakeDataType type);

}
