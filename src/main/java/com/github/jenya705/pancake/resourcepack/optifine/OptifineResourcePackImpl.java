package com.github.jenya705.pancake.resourcepack.optifine;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

@Getter
@Setter(AccessLevel.PROTECTED)
public class OptifineResourcePackImpl implements OptifineResourcePack {

    private File citFolder;

    public OptifineResourcePackImpl(File sourceFolder) {
        citFolder = new File(sourceFolder, "optifine/cit");
        try {
            Files.createDirectories(citFolder.toPath());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    @SneakyThrows
    public OptifineResourcePack add(String name, byte[] bytes) {
        File file = new File(getCitFolder(), name);
        if (!file.exists()) {
            file.createNewFile();
        }
        Files.write(file.toPath(), bytes, StandardOpenOption.WRITE);
        return this;
    }

    @Override
    @SneakyThrows
    public OptifineResourcePack properties(String name, Properties properties) {
        File file = new File(getCitFolder(), name + ".properties");
        if (!file.exists()) file.createNewFile();
        try (FileWriter writer = new FileWriter(file)) {
            properties.store(writer, name);
        }
        return this;
    }
}
