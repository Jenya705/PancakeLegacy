package com.github.jenya705.pancake.resourcepack;

import lombok.Getter;

@Getter
public enum ResourcePackGui {

    ADVANCEMENTS("advancements"),
    ADVANCEMENTS_BACKGROUNDS("advancements/backgrounds"),
    CONTAINER("container"),
    CREATIVE_INVENTORY("container/creative_inventory"),
    PRESETS("presets"),
    TITLE("title"),
    TITLE_BACKGROUND("title/background")
    ;

    private final String directory;
    ResourcePackGui(String directory) {
        this.directory = directory;
    }

}
