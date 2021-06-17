package com.github.jenya705.pancake.enchantment;

import com.github.jenya705.pancake.item.PancakeItemSource;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.Annotation;

/**
 * Builder of {@link PancakeEnchantmentEventHandler}
 */
@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public class PancakeEnchantmentEventHandlerBuilder {

    private String id;
    private PancakeItemSource source = null;

    public PancakeEnchantmentEventHandlerBuilder id(String id) {
        setId(id);
        return this;
    }

    public PancakeEnchantmentEventHandlerBuilder source(PancakeItemSource source) {
        setSource(source);
        return this;
    }

    /**
     * @throws IllegalStateException if required fields did not set or null
     * @return build {@link PancakeEnchantmentEventHandler} annotation
     */
    public PancakeEnchantmentEventHandler build() {
        if (getId() == null || getSource() == null) {
            throw new IllegalStateException("id or source are null");
        }
        return new PancakeEnchantmentEventHandler() {
            @Override
            public String id() {
                return getId();
            }

            @Override
            public PancakeItemSource source() {
                return getSource();
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return PancakeEnchantmentEventHandler.class;
            }
        };
    }

}
