package com.github.jenya705.pancake.data;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;

public class PancakeDataFactoryImpl implements PancakeDataFactory {

    @Override
    public PancakeSerializedData load(String content, PancakeDataType type) {
        if (type == PancakeDataType.YAML) return new YamlSerializedData(content);
        throw new IllegalArgumentException(String.format("Type: %s is not supported", type));
    }

    @Override
    @SneakyThrows
    public PancakeSerializedData load(File file, PancakeDataType type) {
        if (type == PancakeDataType.YAML) return new YamlSerializedData(new FileInputStream(file));
        throw new IllegalArgumentException(String.format("Type: %s is not supported", type));
    }

    @Override
    public PancakeSerializedData load(File file) {
        if (PancakeDataType.YAML.isFile(file.getName())) return load(file, PancakeDataType.YAML);
        throw new IllegalArgumentException(String.format("File: %s is not supported", file.getName()));
    }

    @Override
    public PancakeSerializedData create(PancakeDataType type) {
        if (PancakeDataType.YAML == type) return new YamlSerializedData();
        throw new IllegalArgumentException(String.format("Type: %s is not supported", type));
    }
}
