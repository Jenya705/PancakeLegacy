package com.github.jenya705.pancake.data;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class YamlSerializedData extends PancakeSerializedMapData<String, Object> {

    public static final Yaml yaml = new Yaml();

    public YamlSerializedData() {
        setData(new LinkedHashMap<>());
    }

    public YamlSerializedData(String content) {
        setData(yaml.load(content));
    }

    public YamlSerializedData(Reader reader) {
        setData(yaml.load(reader));
    }

    public YamlSerializedData(InputStream inputStream) {
        setData(yaml.load(inputStream));
    }

    @Override
    public byte[] toBytes() {
        return toDeepString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String toDeepString() {
        Map<String, Object> result = toNativeMap();
        return yaml.dump(result);
    }

    public Map<String, Object> toNativeMap() {
        Map<String, Object> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : getData().entrySet()) {
            Object value = entry.getValue();
            if (value instanceof YamlSerializedData) result.put(entry.getKey(), ((YamlSerializedData) value).toNativeMap());
            else result.put(entry.getKey(), value);
        }
        return result;
    }
}
