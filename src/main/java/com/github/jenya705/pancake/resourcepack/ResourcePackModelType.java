package com.github.jenya705.pancake.resourcepack;

import lombok.Getter;

@Getter
public enum ResourcePackModelType {

    MODEL("model"),
    ITEM("item")
    ;

    private final String type;
    ResourcePackModelType(String type) {
        this.type = type;
    }

}
