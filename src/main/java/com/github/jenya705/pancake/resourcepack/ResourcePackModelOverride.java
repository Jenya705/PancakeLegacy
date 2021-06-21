package com.github.jenya705.pancake.resourcepack;

import java.util.Map;

/**
 * Resource pack model override
 */
public interface ResourcePackModelOverride {

    /**
     *
     * Return builder
     *
     * @return builder
     */
    static ResourcePackModelOverrideImpl.ResourcePackModelOverrideImplBuilder builder() {
        return ResourcePackModelOverrideImpl.builder();
    }

    /**
     *
     * Return model field of override
     *
     * @return model field of override
     */
    String getModel();

    /**
     *
     * Return predicate field of override
     *
     * @return predicate field of override
     */
    Map<String, Object> getPredicate();

    /**
     *
     * Return object as json map (String, Object)
     *
     * @return Object as json map (String, Object)
     */
    Map<String, Object> asMap();

}
