package com.github.jenya705.pancake.data;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;

public class YamlData extends PancakeMapData<String, Object> {

    public static final Yaml yaml = new Yaml();

    public YamlData() {
        setData(new LinkedHashMap<>());
    }

    public YamlData(String content) {
        setData(yaml.load(content));
        if (getData() == null) setData(new LinkedHashMap<>());
    }

    public YamlData(Reader reader) {
        setData(yaml.load(reader));
        if (getData() == null) setData(new LinkedHashMap<>());
    }

    public YamlData(InputStream inputStream) {
        setData(yaml.load(inputStream));
        if (getData() == null) setData(new LinkedHashMap<>());
    }

    @Override
    public byte[] toBytes() {
        return toDeepString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String toDeepString() {
        return yaml.dump(toNativeMap());
    }

    @Override
    public PancakeMapData<String, Object> createSelf() {
        return new YamlData();
    }
}
