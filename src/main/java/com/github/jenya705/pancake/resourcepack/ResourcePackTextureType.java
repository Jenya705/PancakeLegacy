package com.github.jenya705.pancake.resourcepack;

import lombok.Getter;

@Getter
public enum ResourcePackTextureType {
    BLOCK("block"),
    COLOR_MAP("colormap"),
    EFFECT("effect"),
    ENTITY("entity"),
    ENVIRONMENT("environment"),
    FONT("font"),
    GUI("gui"),
    ITEM("item"),
    MAP("map"),
    MISC("misc"),
    MOB_EFFECT("mob_effect"),
    MODELS("models"),
    PAINTING("painting"),
    PARTICLE("particle")
    ;

    private final String directory;
    ResourcePackTextureType(String directory) {
        this.directory = directory;
    }

}
