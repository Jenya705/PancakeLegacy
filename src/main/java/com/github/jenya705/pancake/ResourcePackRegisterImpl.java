package com.github.jenya705.pancake;

import com.github.jenya705.pancake.item.container.PancakeItemContainer;
import com.github.jenya705.pancake.item.model.CustomModelArmor;
import com.github.jenya705.pancake.item.model.CustomModelItem;
import com.github.jenya705.pancake.resourcepack.ResourcePackModel;
import com.github.jenya705.pancake.resourcepack.ResourcePackModelType;
import com.github.jenya705.pancake.resourcepack.ResourcePackTextureType;
import com.github.jenya705.pancake.resourcepack.optifine.OptifineResourcePack;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;

@Getter
@Setter(AccessLevel.PROTECTED)
public class ResourcePackRegisterImpl implements ResourcePackRegister {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private Map<Material, ResourcePackModel> materialModels = new HashMap<>();

    @Override
    public void registerItemModel(CustomModelItem customModelItem, PancakeItemContainer<?> itemContainer, JavaPlugin plugin) {
        Pancake pancake = Pancake.getPlugin();
        ResourcePackModel model;
        if (!getMaterialModels().containsKey(itemContainer.getMaterial())) {
            model = ResourcePackModel.builder()
                    .parent("minecraft:item/generated")
                    .texture("layer0", "minecraft:item/" + itemContainer.getMaterial().name().toLowerCase(Locale.ROOT))
                    .overrides(new ArrayList<>())
                    .build();
            getMaterialModels().put(itemContainer.getMaterial(), model);
        }
        else {
            model = getMaterialModels().get(itemContainer.getMaterial());
        }
        customModelItem.apply(model, itemContainer, itemContainer.getCustomModelData());
        pancake.getResourcePack().model(ResourcePackModelType.ITEM, customModelItem.getModelName() + ".json",
                gson.toJson(
                        ResourcePackModel.builder()
                                .parent("minecraft:item/generated")
                                .texture("layer0", "item/" + customModelItem.getModelName())
                                .build()
                                .asMap()
                )
        );
        if (customModelItem.isLoadItemTextures()) {
            try {
                pancake.getResourcePack().texture(
                        ResourcePackTextureType.ITEM,
                        customModelItem.getModelName() + ".png",
                        plugin.getResource("assets/" + customModelItem.getModelName() + ".png").readAllBytes()
                );
            } catch (IOException | NullPointerException e) {
                plugin.getLogger().log(Level.WARNING, "[Pancake] Exception while loading texture of item:", e);
            }
        }
        pancake.getResourcePack().model(
                ResourcePackModelType.ITEM,
                itemContainer.getMaterial().name().toLowerCase(Locale.ROOT) + ".json",
                gson.toJson(
                    getMaterialModels().get(itemContainer.getMaterial()).asMap()
                )
        );
    }

    @Override
    public void registerArmorModel(CustomModelArmor customModelArmor, PancakeItemContainer<?> itemContainer, JavaPlugin plugin) {
        Pancake pancake = Pancake.getPlugin();
        OptifineResourcePack optifineResourcePack = pancake.getResourcePack().optifine();
        customModelArmor.apply(optifineResourcePack, itemContainer, itemContainer.getCustomModelData());
        if (customModelArmor.isLoadArmorTextures()) {
            if (customModelArmor.isLayer1()) {
                loadLayer(customModelArmor, optifineResourcePack, 1, plugin);
            }
            if (customModelArmor.isLayer2()) {
                loadLayer(customModelArmor, optifineResourcePack, 2, plugin);
            }
        }
    }

    protected void loadLayer(CustomModelArmor customModelArmor, OptifineResourcePack optifineResourcePack, int layerNumber, JavaPlugin plugin) {
        try {
            optifineResourcePack.add(
                    customModelArmor.getModelName() + "_layer_" + layerNumber + ".png",
                    plugin.getResource("assets/" + customModelArmor.getModelName() + "_layer_" + layerNumber + ".png").readAllBytes()
            );
        } catch (IOException | NullPointerException e) {
            plugin.getLogger().log(Level.SEVERE, "[Pancake] Exception while loading layer_" + layerNumber + ":", e);
        }
    }
}
