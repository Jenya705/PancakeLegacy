package com.github.jenya705.pancake.resourcepack;

import lombok.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.entity.EntityType;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Locale;

@Getter
@Setter(AccessLevel.PROTECTED)
public class ResourcePackImpl implements ResourcePack {

    private File namespaceFolder;

    public ResourcePackImpl(File namespaceFolder) {
        setNamespaceFolder(new File(namespaceFolder, "/assets/minecraft"));
        try {
            getNamespaceFolder().delete();
            createDirectories(
                    ".", "blockstates", "font", "icons",
                    "lang", "models", "models/block", "models/item",
                    "particles", "sounds", "shaders", "shaders/post",
                    "shaders/program", "texts", "textures"
            );
            for (ResourcePackTextureType type : ResourcePackTextureType.values()) {
                createDirectories("textures/" + type.getDirectory());
            }
            for (ResourcePackGui type: ResourcePackGui.values()) {
                createDirectories("textures/gui/" + type.getDirectory());
            }
            for (EntityType entityType: EntityType.values()) {
                createDirectories("textures/entity/" + entityType.name().toLowerCase(Locale.ROOT));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected void createDirectories(String... dirs) throws IOException {
        for (String dir: dirs) {
            File file = new File(getNamespaceFolder(), dir);
            Files.createDirectories(file.toPath());
        }
    }

    @Override
    public String getNamespace() {
        return getNamespaceFolder().getName();
    }

    @Override
    @SneakyThrows
    public ResourcePack add(String fileDirectory, byte[] bytes) {
        File file = new File(namespaceFolder, fileDirectory);
        if (!file.exists()) {
            file.createNewFile();
        }
        Files.write(file.toPath(), bytes, StandardOpenOption.WRITE);
        return this;
    }

    @Override
    public ResourcePack meta(int packVersion, String description) {
        return add("../../pack.mcmeta",
                String.format("""
                        {
                            "pack": {
                                "description": "%s",
                                "pack_format": %s
                            }
                        }
                        """, description, packVersion)
                        .getBytes(StandardCharsets.UTF_8)
                );
    }

    @Override
    public ResourcePack meta(int packVersion, Component description) {
        return add("../../pack.mcmeta",
                String.format("""
                        {
                            "pack": {
                                "description": %s,
                                "pack_format": %s
                            }
                        }
                        """, GsonComponentSerializer.gson().serialize(description), packVersion)
                        .getBytes(StandardCharsets.UTF_8)
        );
    }

    @Override
    public ResourcePack texture(String directory, String texture, byte[] imageBytes) {
        return add("textures/" + directory + "/" + texture, imageBytes);
    }

    @Override
    public ResourcePack texture(ResourcePackTextureType directory, String texture, byte[] imageBytes) {
        return texture(directory.getDirectory(), texture, imageBytes);
    }

    @Override
    public ResourcePack texture(EntityType entityType, String texture, byte[] imageBytes) {
        return texture("entity/" + entityType.name().toLowerCase(Locale.ROOT), texture, imageBytes);
    }

    @Override
    public ResourcePack gui(String directory, String texture, byte[] imageBytes) {
        return texture("gui/" + directory, texture, imageBytes);
    }

    @Override
    public ResourcePack gui(ResourcePackGui directory, String texture, byte[] imageBytes) {
        return gui(directory.getDirectory(), texture, imageBytes);
    }

    @Override
    public ResourcePack gpuWarnList(String content) {
        return add("gpu_warnlist.json", content.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public ResourcePack text(String name, String content) {
        return add("texts/" + name + ".txt", content.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public ResourcePack text(ResourcePackTextType type, String content) {
        return text(type.getName(), content);
    }

    @Override
    public ResourcePack blockState(String name, String content) {
        return add("blockstates/" + name, content.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public ResourcePack model(String type, String name, String content) {
        return add("models/" + type + "/"+ name, content.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public ResourcePack model(ResourcePackModelType type, String name, String content) {
        return model(type.getType(), name, content);
    }
}
