package com.github.jenya705.pancake.resourcepack;

import com.github.jenya705.pancake.resourcepack.optifine.OptifineResourcePack;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;

import java.io.File;

/**
 * Resource pack builder
 */
public interface ResourcePack {

    /**
     * @param namespaceFile Namespace File
     * @return Resource pack implementation (automatic add files)
     */
    static ResourcePack of(File namespaceFile) {
        return new ResourcePackImpl(namespaceFile);
    }

    /**
     * @return Optifine resource pack builder
     */
    OptifineResourcePack optifine();

    /**
     * @return namespace of resource pack
     */
    String getNamespace();

    /**
     *
     * Add file to resource pack
     *
     * @param file File directory
     * @param bytes File bytes
     * @return Resource pack with added file
     */
    ResourcePack add(String file, byte[] bytes);

    /**
     * Creates mcmeta file (without language)
     *
     * @param packVersion Version of resource pack
     * @param description Description of resource pack as raw text
     * @return resource pack with meta
     */
    ResourcePack meta(int packVersion, String description);

    /**
     *
     * Creates mcmeta file (without language)
     *
     * @param packVersion Version of resource pack
     * @param description Description of resource pack as json
     * @return resource pack with meta
     */
    ResourcePack meta(int packVersion, Component description);

    /**
     *
     * Add texture
     *
     * @param directory Texture directory
     * @param texture Texture name
     * @param imageBytes Image bytes
     * @return resource pack with added texture
     */
    ResourcePack texture(String directory, String texture, byte[] imageBytes);

    /**
     *
     * Add texture
     *
     * @param directory Texture directory
     * @param texture Texture name
     * @param imageBytes Image bytes
     * @return resource pack with added texture
     */
    default ResourcePack texture(ResourcePackTextureType directory, String texture, byte[] imageBytes) {
        return texture(directory.getDirectory(), texture, imageBytes);
    }

    /**
     *
     * Add texture
     *
     * @param entityType Entity type
     * @param texture Texture name
     * @param imageBytes Image bytes
     * @return resource pack with added texture
     */
    ResourcePack texture(EntityType entityType, String texture, byte[] imageBytes);

    /**
     *
     * Add gui texture
     *
     * @param directory Gui directory
     * @param texture File name
     * @param imageBytes Image bytes
     * @return resource pack with added texture
     */
    ResourcePack gui(String directory, String texture, byte[] imageBytes);

    /**
     *
     * Add gui texture
     *
     * @param directory Gui directory
     * @param texture File name
     * @param imageBytes Image bytes
     * @return resource pack with added texture
     */
    ResourcePack gui(ResourcePackGui directory, String texture, byte[] imageBytes);

    /**
     *
     * Add gpu_warnlist.json
     *
     * @param content File content
     * @return resource pack with added gpu_warnlist.json
     */
    ResourcePack gpuWarnList(String content);

    /**
     *
     * Add text with name (.txt added)
     *
     * @param name name of text
     * @param content File content
     * @return resource pack with added text
     */
    ResourcePack text(String name, String content);

    /**
     *
     * Add text with type
     *
     * @param type Type of text
     * @param content File content
     * @return resource pack with added text
     */
    ResourcePack text(ResourcePackTextType type, String content);

    /**
     *
     * Add block state to resource pack
     *
     * @param name Block state name (File name)
     * @param content Content of block state file
     * @return Resource pack with added block state
     */
    ResourcePack blockState(String name, String content);

    /**
     *
     * Add model to resource pack
     *
     * @param type model type
     * @param name name of model
     * @param content content of file
     * @return Resource pack with added model
     */
    ResourcePack model(String type, String name, String content);

    /**
     *
     * Add model to resource pack
     *
     * @param type model type
     * @param name name of model (file name)
     * @param content content of file
     * @return Resource pack with added model
     */
    ResourcePack model(ResourcePackModelType type, String name, String content);

}
