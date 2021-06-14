package com.github.jenya705.pancake;

import com.github.jenya705.pancake.data.PancakeData;
import com.github.jenya705.pancake.data.PancakeDataType;
import com.github.jenya705.pancake.enchantment.*;
import com.github.jenya705.pancake.item.*;
import com.github.jenya705.pancake.item.event.PancakeItemEvent;
import com.github.jenya705.pancake.item.model.CustomModelItem;
import com.github.jenya705.pancake.resourcepack.ResourcePackModel;
import com.github.jenya705.pancake.resourcepack.ResourcePackModelImpl;
import com.github.jenya705.pancake.resourcepack.ResourcePackModelType;
import com.github.jenya705.pancake.resourcepack.ResourcePackTextureType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.logging.Level;

@Getter
@Setter(AccessLevel.PROTECTED)
public class PancakeRegisterImpl implements PancakeRegister {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private Map<String, PancakeItemContainer<?>> items = new HashMap<>();
    private Map<String, PancakeEnchantmentContainer<?>> enchantments = new HashMap<>();

    @SneakyThrows
    @Override
    public void registerAll(String packageName, JavaPlugin plugin) {
        Reflections packageReflections = new Reflections(packageName);
        Set<Class<?>> itemClasses = packageReflections.getTypesAnnotatedWith(PancakeItem.class);
        for (Class<?> itemClass: itemClasses) {
            Constructor<?> constructor;
            try {
                constructor = itemClass.getConstructor();
            } catch (NoSuchMethodException e) {
                plugin.getLogger().warning(String.format(
                        "[Pancake] To automatically register item, " +
                                "it needs void constructor. Class: %s", itemClass.getName()));
                continue;
            }
            registerItem(constructor.newInstance(), plugin);
        }
        Set<Class<?>> enchantClasses = packageReflections.getTypesAnnotatedWith(PancakeEnchantment.class);
        for (Class<?> enchantClass: enchantClasses) {
            Constructor<?> constructor;
            try {
                constructor = enchantClass.getConstructor();
            } catch (NoSuchMethodException e) {
                plugin.getLogger().warning(String.format(
                        "[Pancake] To automatically register enchantment, " +
                                "it needs void constructor. Class: %s", enchantClass.getName()));
                continue;
            }
            registerEnchantment(constructor.newInstance(), plugin);
        }
        getItems().forEach((id, itemContainer) -> {
            try {
                plugin.getLogger().info(String.format("[Pancake] Register item %s", id));
                registerOneElement(itemContainer.getSource(), id, itemContainer, null, plugin);
            } catch (Exception e) {
                plugin.getLogger().log(Level.WARNING, "[Pancake] Exception while trying to load item:", e);
            }
        });
        getEnchantments().forEach((id, enchantContainer) -> {
            try {
                plugin.getLogger().info(String.format("[Pancake] Register enchantment %s", id));
                registerOneElement(enchantContainer.getSource(), id, null, enchantContainer, plugin);
            } catch (Exception e) {
                plugin.getLogger().log(Level.WARNING, "[Pancake] Exception while trying to load enchant:", e);
            }
        });
        applyCustomModels(plugin);
    }

    public void applyCustomModels(JavaPlugin plugin) {
        Map<Material, ResourcePackModelImpl.ResourcePackModelImplBuilder> materialBuilders = new HashMap<>();
        Pancake pancake = Pancake.getPlugin();
        getItems().forEach((id, itemContainer) -> {
            if (!(itemContainer.getSource() instanceof CustomModelItem customModelItem)) return;
            ResourcePackModelImpl.ResourcePackModelImplBuilder builder;
            if (!materialBuilders.containsKey(itemContainer.getMaterial())) {
                builder = ResourcePackModel.builder();
                builder
                        .parent("minecraft:item/generated")
                        .texture("layer0", "minecraft:item/" + itemContainer.getMaterial().name().toLowerCase(Locale.ROOT));
                materialBuilders.put(itemContainer.getMaterial(), builder);
            }
            else {
                builder = materialBuilders.get(itemContainer.getMaterial());
            }
            customModelItem.apply(builder, itemContainer.getCustomModelData());
            pancake.getResourcePack().model(ResourcePackModelType.ITEM, customModelItem.getModelName() + ".json",
                    gson.toJson(
                            ResourcePackModel.builder()
                                    .parent("minecraft:item/generated")
                                    .texture("layer0", "item/" + customModelItem.getModelName())
                                    .build()
                                    .asMap()
                    )
            );
            try {
                pancake.getResourcePack().texture(
                        ResourcePackTextureType.ITEM,
                        customModelItem.getModelName() + ".png",
                        plugin.getResource(customModelItem.getModelName() + ".png").readAllBytes()
                );
            } catch (IOException | NullPointerException e) {
                plugin.getLogger().log(Level.WARNING, "Exception while loading texture of item:", e);
            }
        });
        materialBuilders.forEach((material, builder) ->
            pancake.getResourcePack().model(ResourcePackModelType.ITEM, material.name().toLowerCase(Locale.ROOT) + ".json",
                    gson.toJson(
                            builder
                                    .build()
                                    .asMap()
                    )
            )
        );
    }

    protected void registerOneElement(Object source, String id, PancakeItemContainer<?> itemContainer, PancakeEnchantmentContainer<?> enchantmentContainer, JavaPlugin plugin) {
        if (source instanceof Listener) {
            plugin.getServer().getPluginManager().registerEvents((Listener) source, plugin);
        }
        if (source instanceof PancakeItemListener) {
            registerPancakeItemListener((PancakeItemListener) source, itemContainer == null ? null : itemContainer.getID(), plugin);
        }
        if (source instanceof PancakeEnchantmentListener) {
            registerPancakeEnchantmentListener((PancakeEnchantmentListener) source, enchantmentContainer == null ? null : enchantmentContainer.getId(), plugin);
        }
    }

    public void configurable(PancakeConfigurable configurable, String id, JavaPlugin plugin) {
        Pancake pancake = Pancake.getPlugin();
        try {
            File configsDir = new File(plugin.getDataFolder(), "configs");
            if (!configsDir.exists()) configsDir.mkdir();
            File file = new File(configsDir, id.replaceAll(":", ".") + ".yml");
            if (!file.exists()) file.createNewFile();
            else configurable.load(pancake.getDataFactory().load(file, PancakeDataType.YAML));
            PancakeData serializedData = pancake.getDataFactory().create(PancakeDataType.YAML);
            configurable.save(serializedData);
            Files.write(file.toPath(), serializedData.toBytes(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, String.format("[Pancake] Configurable Pancake object can not be loaded/saved. Id: %s", id), e);
        }
    }

    @Override
    public List<String> getItemNames() {
        return new ArrayList<>(getItems().keySet());
    }

    @Override
    public List<String> getEnchantmentNames() {
        return new ArrayList<>(getEnchantments().keySet());
    }

    @Override
    public PancakeItemContainer<?> getItemContainer(String id) {
        return getItems().getOrDefault(id.toLowerCase(Locale.ROOT), null);
    }

    @Override
    public PancakeEnchantmentContainer<?> getEnchantmentContainer(String id) {
        return getEnchantments().getOrDefault(id.toLowerCase(Locale.ROOT), null);
    }

    @Override
    public void registerItem(Object item, JavaPlugin plugin) {
        PancakeItem annotation = item.getClass().getAnnotation(PancakeItem.class);
        if (annotation == null) throw new IllegalArgumentException("Object does not have PancakeItem annotation");
        registerItem(item, annotation, plugin);
    }

    @Override
    public void registerItem(Object item, PancakeItem annotation, JavaPlugin plugin) {
        Pancake pancake = Pancake.getPlugin();
        getItems().put(annotation.id().toLowerCase(Locale.ROOT),
                new PancakeItemContainerImpl<>(item, annotation,
                item instanceof CustomModelItem ? pancake.getCustomModelDataContainer().getCustomModelData(item, annotation) : 0 // (0 - default custom model data)
        ));
        if (item instanceof PancakeConfigurable) {
            configurable((PancakeConfigurable) item, annotation.id(), plugin);
        }
    }

    @Override
    public void registerEnchantment(Object enchantment, JavaPlugin plugin) {
        PancakeEnchantment annotation = enchantment.getClass().getAnnotation(PancakeEnchantment.class);
        if (annotation == null) throw new IllegalArgumentException("Object does not have PancakeEnchantment annotation");
        registerEnchantment(enchantment, annotation, plugin);
    }

    @Override
    public void registerEnchantment(Object enchantment, PancakeEnchantment annotation, JavaPlugin plugin) {
        getEnchantments().put(annotation.id().toLowerCase(Locale.ROOT), new PancakeEnchantmentContainerImpl<>(enchantment, annotation));
        if (enchantment instanceof PancakeConfigurable) {
            configurable((PancakeConfigurable) enchantment, annotation.id(), plugin);
        }
    }

    @Override
    public void registerPancakeItemListener(PancakeItemListener listener, String defaultID, JavaPlugin plugin) {
        Class<? extends PancakeItemListener> clazz = listener.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method: methods) {
            PancakeItemEventHandler[] annotations = method.getAnnotationsByType(PancakeItemEventHandler.class);
            if (annotations == null) continue;
            for (PancakeItemEventHandler annotation: annotations) {
                if (annotation == null) continue;
                Class<?> types[] = method.getParameterTypes();
                try {
                    if (types.length == 1) {
                        registerPancakeItemEventHandler(
                                (event) -> {
                                    try {
                                        method.invoke(listener, event);
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                },
                                (Class<? extends PancakeItemEvent>) types[0],
                                annotation,
                                defaultID,
                                plugin
                        );
                    } else {
                        plugin.getLogger().warning(String.format(
                                "[Pancake] Only 1 arguments method can be registered, Class: %s Method: %s",
                                listener.getClass().getName(), method.getName()
                        ));
                    }
                } catch (Exception e) {
                    plugin.getLogger().log(Level.WARNING, String.format(
                            "[Pancake] Can not subscribe event handler. Class: %s, Method: %s",
                            listener.getClass().getName(), method.getName()
                    ), e);
                }
            }
        }
    }

    @Override
    public void registerPancakeEnchantmentListener(PancakeEnchantmentListener listener, String defaultID, JavaPlugin plugin) {
        Class<? extends PancakeEnchantmentListener> clazz = listener.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method: methods) {
            PancakeEnchantmentEventHandler[] annotations = method.getAnnotationsByType(PancakeEnchantmentEventHandler.class);
            if (annotations == null) continue;
            for (PancakeEnchantmentEventHandler annotation: annotations) {
                if (annotation == null) continue;
                Class<?> types[] = method.getParameterTypes();
                try {
                    if (types.length == 2) {
                        registerPancakeEnchantmentEventHandler(
                                (event, enchantmentObject) -> {
                                    try {
                                        method.invoke(listener, event, enchantmentObject);
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                    return null;
                                },
                                (Class<? extends PancakeItemEvent>) types[0],
                                annotation,
                                defaultID,
                                plugin
                        );
                    } else {
                        plugin.getLogger().warning(String.format(
                                "[Pancake] Only 2 arguments method can be registered, Class: %s Method: %s",
                                listener.getClass().getName(), method.getName()
                        ));
                    }
                } catch (Exception e) {
                    plugin.getLogger().log(Level.WARNING, String.format(
                            "[Pancake] Can not subscribe event handler. Class: %s, Method: %s",
                            listener.getClass().getName(), method.getName()), e);
                }
            }
        }
    }

    @Override
    public void registerPancakeItemEventHandler(Consumer<PancakeItemEvent> function, Class<? extends PancakeItemEvent> clazz,
                                                PancakeItemEventHandler handlerAnnotation, String defaultID, JavaPlugin plugin) {
        PancakeItemContainer<?> itemContainer = (defaultID == null ? null : getItemContainer(defaultID));
        if (itemContainer == null && handlerAnnotation.id().isEmpty()) {
            throw new IllegalArgumentException(
                    "Object is not Pancake item so ID of " +
                            "PancakeItemEventHandler annotation cannot be empty"
            );
        }
        PancakeItemContainer<?> currentContainer = handlerAnnotation.id().isEmpty() ? itemContainer : getItemContainer(handlerAnnotation.id());
        if (currentContainer == null) {
            throw new IllegalArgumentException(String.format("Id %s is not exist", handlerAnnotation.id()));
        }
        currentContainer.addHandler(
                clazz, handlerAnnotation.source(), function
        );
    }

    @Override
    public void registerPancakeEnchantmentEventHandler(BiFunction<PancakeItemEvent, PancakeEnchantmentObject, Void> function,
                                                       Class<? extends PancakeItemEvent> clazz,
                                                       PancakeEnchantmentEventHandler handlerAnnotation, String defaultID, JavaPlugin plugin) {
        PancakeEnchantmentContainer<?> enchantmentContainer = (defaultID == null ? null : getEnchantmentContainer(defaultID));
        if (enchantmentContainer == null && handlerAnnotation.id().isEmpty()) {
            throw new IllegalArgumentException(
                    "Object is not Pancake enchantment so ID of " +
                            "PancakeItemEventHandler annotation cannot be empty"
            );
        }
        PancakeEnchantmentContainer<?> currentContainer = handlerAnnotation.id().isEmpty() ? enchantmentContainer : getEnchantmentContainer(handlerAnnotation.id());
        if (currentContainer == null) {
            throw new IllegalArgumentException(String.format("Id %s is not exist", handlerAnnotation.id()));
        }
        currentContainer.addHandler(
                clazz, handlerAnnotation.source(), function
        );
    }

}
