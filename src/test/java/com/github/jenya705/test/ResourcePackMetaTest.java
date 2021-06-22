package com.github.jenya705.test;

import com.github.jenya705.pancake.resourcepack.ResourcePack;
import com.github.jenya705.pancake.resourcepack.ResourcePackImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.SneakyThrows;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ResourcePackMetaTest {

    @Getter
    public static class ResourcePackWithContainer extends ResourcePackImpl {

        private byte[] container;

        @Override
        public ResourcePack add(String fileDirectory, byte[] bytes) {
            container = bytes;
            return this;
        }

        public ResourcePackWithContainer() {
            super(null);
        }
    }

    @SneakyThrows
    @Test
    public void metaTest() {
        ResourcePackWithContainer resourcePackWithContainer = new ResourcePackWithContainer();
        resourcePackWithContainer.meta(6, "Pancake!");
        String metaGenerated = new String(resourcePackWithContainer.getContainer(), StandardCharsets.UTF_8);
        Map<String, Object> map = (Map<String, Object>) new JSONParser().parse(metaGenerated);
        Assertions.assertTrue(map.containsKey("pack"));
        Map<String, Object> pack = (Map<String, Object>) map.get("pack");
        Assertions.assertTrue(pack.containsKey("pack_format"));
        Assertions.assertTrue(pack.containsKey("description"));
        Assertions.assertEquals(6L, pack.get("pack_format"));
        Assertions.assertEquals("Pancake!", pack.get("description"));
        Assertions.assertEquals(1, map.size());
        Assertions.assertEquals(2, pack.size());
    }

}
