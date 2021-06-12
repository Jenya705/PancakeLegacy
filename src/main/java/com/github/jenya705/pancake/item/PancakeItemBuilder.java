package com.github.jenya705.pancake.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.lang.annotation.Annotation;

/**
 * Builder of {@link PancakeItem}
 */
@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public class PancakeItemBuilder {

    private String name;
    private String id;
    private Material material;

    public static PancakeItemBuilder builder() { return new PancakeItemBuilder(); }

    public PancakeItemBuilder name(String name) {
        setName(name);
        return this;
    }

    public PancakeItemBuilder id(String id) {
        setId(id);
        return this;
    }

    public PancakeItemBuilder material(Material material) {
        setMaterial(material);
        return this;
    }

    public PancakeItem build() {
        return new PancakeItem() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return PancakeItem.class;
            }

            @Override
            public String name() {
                return getName();
            }

            @Override
            public String id() {
                return getId();
            }

            @Override
            public Material material() {
                return getMaterial();
            }
        };
    }

}
