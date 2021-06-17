package com.github.jenya705.pancake.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.Annotation;

/**
 * Builder of {@link PancakeItemEventHandler}
 */
@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public class PancakeItemEventHandlerBuilder {

    private String id;
    private PancakeItemSource source = null;

    public PancakeItemEventHandlerBuilder id(String id) {
        setId(id);
        return this;
    }

    public PancakeItemEventHandlerBuilder source(PancakeItemSource source) {
        setSource(source);
        return this;
    }

    /**
     * @throws IllegalStateException if required fields did not set or null
     * @return build {@link PancakeItemEventHandler} annotation
     */
    public PancakeItemEventHandler build() {
        if (getId() == null || getSource() == null) {
            throw new IllegalStateException("id or source are null");
        }
        return new PancakeItemEventHandler() {
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
                return PancakeItemEventHandler.class;
            }
        };
    }

}
