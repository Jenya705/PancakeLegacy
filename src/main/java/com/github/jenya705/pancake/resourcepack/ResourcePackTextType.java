package com.github.jenya705.pancake.resourcepack;

import lombok.Getter;

@Getter
public enum ResourcePackTextType {

    CREDITS("credits"),
    END("end"),
    SPLASHES("splashes")
    ;

    private final String name;
    ResourcePackTextType(String name) {
        this.name = name;
    }

}
