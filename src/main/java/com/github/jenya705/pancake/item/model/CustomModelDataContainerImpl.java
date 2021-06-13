package com.github.jenya705.pancake.item.model;

import com.github.jenya705.pancake.Pancake;
import com.github.jenya705.pancake.data.PancakeData;
import com.github.jenya705.pancake.item.PancakeItem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
public class CustomModelDataContainerImpl implements CustomModelDataContainer {

    public static final String fileName = "custom_model_data.yml"; // YAML file

    private PancakeData data;

    public CustomModelDataContainerImpl() throws IOException {
        Pancake pancake = Pancake.getPlugin();
        File file = new File(pancake.getDataFolder(), fileName);
        if (!file.exists()) file.createNewFile();
        setData(pancake.getDataFactory().load(file));
    }

    @Override
    public int getCustomModelData(Object source, PancakeItem annotation) {
        if (!(source instanceof CustomModelItem)) {
            throw new IllegalArgumentException("Item is not CustomModelItem");
        }
        List<Object> materialCustomModelData = getData().getArray(annotation.material().name(), null);
        if (materialCustomModelData == null) {
            getData().set(annotation.material().name(), materialCustomModelData = new ArrayList<>());
        }
        int i = 1;
        for (Object obj: materialCustomModelData) {
            if (obj instanceof String && ((String) obj).equalsIgnoreCase(annotation.id())) {
                return i;
            }
            ++i;
        }
        materialCustomModelData.add(annotation.id());
        return materialCustomModelData.size();
    }

    @Override
    @SneakyThrows
    public void save() {
        Pancake pancake = Pancake.getPlugin();
        File file = new File(pancake.getDataFolder(), fileName);
        if (!file.exists()) file.createNewFile();
        Files.write(file.toPath(), getData().toBytes(), StandardOpenOption.WRITE);
    }
}
