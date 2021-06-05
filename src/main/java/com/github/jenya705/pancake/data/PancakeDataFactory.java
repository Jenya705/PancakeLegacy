package com.github.jenya705.pancake.data;

import java.io.File;

public interface PancakeDataFactory {

    PancakeSerializedData load(String content, PancakeDataType type);

    PancakeSerializedData load(File file, PancakeDataType type);

    PancakeSerializedData load(File file);

    PancakeSerializedData create(PancakeDataType type);

}
