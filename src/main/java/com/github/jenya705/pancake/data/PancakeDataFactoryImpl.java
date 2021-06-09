package com.github.jenya705.pancake.data;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;

public class PancakeDataFactoryImpl implements PancakeDataFactory {

    @Override
    public PancakeData load(String content, PancakeDataType type) {
        if (type == PancakeDataType.YAML) return new YamlData(content);
        throw new IllegalArgumentException(String.format("Type: %s is not supported", type));
    }

    @Override
    @SneakyThrows
    public PancakeData load(File file, PancakeDataType type) {
        FileInputStream is;
        PancakeData data;
        if (type == PancakeDataType.YAML) data = new YamlData(is = new FileInputStream(file));
        else throw new IllegalArgumentException(String.format("Type: %s is not supported", type));
        is.close();
        return data;
    }

    @Override
    public PancakeData load(File file) {
        if (PancakeDataType.YAML.isFile(file.getName())) return load(file, PancakeDataType.YAML);
        throw new IllegalArgumentException(String.format("File: %s is not supported", file.getName()));
    }

    @Override
    public PancakeData create(PancakeDataType type) {
        if (PancakeDataType.YAML == type) return new YamlData();
        throw new IllegalArgumentException(String.format("Type: %s is not supported", type));
    }
}
