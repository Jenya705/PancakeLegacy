package com.github.jenya705.pancake.data;

import lombok.Getter;

@Getter
public enum PancakeDataType {

    YAML(new String[]{"yaml", "yml"});

    private final String[] extensions;
    PancakeDataType(String[] extensions) {
        this.extensions = extensions;
    }

    public boolean isFile(String fileName) {
        for (String extension: getExtensions()) {
            if (fileName.endsWith(extension)) return true;
        }
        return false;
    }

}
