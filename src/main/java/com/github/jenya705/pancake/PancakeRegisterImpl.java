package com.github.jenya705.pancake;

import com.github.jenya705.pancake.data.PancakeDataType;
import com.github.jenya705.pancake.data.PancakeSerializedData;
import com.github.jenya705.pancake.item.*;
import com.github.jenya705.pancake.item.event.PancakeItemEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

@Getter
@Setter(AccessLevel.PROTECTED)
public class PancakeRegisterImpl implements PancakeRegister {

    private Map<String, PancakeItemContainer<?>> items = new HashMap<>();

    @SneakyThrows
    @Override
    public void registerAll(String packageName, JavaPlugin plugin) {
        Reflections packageReflections = new Reflections(packageName);
        Set<Class<?>> itemsClasses = packageReflections.getTypesAnnotatedWith(PancakeItem.class);
        for (Class<?> itemClass: itemsClasses) {
            PancakeItemContainer<?> itemContainer = new PancakeItemContainerImpl<>(itemClass.getConstructor().newInstance());
            getItems().put(itemContainer.getID(), itemContainer);
        }
        getItems().forEach((id, itemContainer) -> {
            try {
                if (itemContainer.getSource() instanceof Listener) {
                    plugin.getServer().getPluginManager().registerEvents((Listener) itemContainer.getSource(), plugin);
                }
                if (itemContainer.getSource() instanceof PancakeConfigurable) {
                    configurable((PancakeConfigurable) itemContainer.getSource(), id, plugin);
                }
                if (itemContainer.getSource() instanceof PancakeItemListener) {
                    item(itemContainer, (PancakeItemListener) itemContainer.getSource(), plugin);
                }
            } catch (Exception e) {
                plugin.getLogger().log(Level.WARNING, "Exception while trying to load item:", e);
            }
        });
    }

    public void configurable(PancakeConfigurable configurable, String id, JavaPlugin plugin) {
        Pancake pancake = Pancake.getPlugin();
        try {
            File configsDir = new File(plugin.getDataFolder(), "configs");
            if (!configsDir.exists()) configsDir.mkdir();
            File file = new File(configsDir, id + ".yml");
            if (!file.exists()) file.createNewFile();
            else configurable.load(pancake.getDataFactory().load(file, PancakeDataType.YAML));
            PancakeSerializedData serializedData = pancake.getDataFactory().create(PancakeDataType.YAML);
            configurable.save(serializedData);
            Files.write(file.toPath(), serializedData.toBytes(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Configurable Pancake object can not be loaded/saved:", e);
        }
    }

    public void item(PancakeItemContainer<?> container, PancakeItemListener listener, JavaPlugin plugin) {
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
                        PancakeItemContainer<?> currentContainer = annotation.id().isEmpty() ? container : PancakeItemUtils.getItemContainer(annotation.id());
                        if (container == null) {
                            plugin.getLogger().warning(String.format(
                                            "Object is not Pancake item so ID of PancakeItemEventHandler " +
                                            "annotation cannot be empty. Class: %s, Method: %s",
                                            clazz.getName(), method.getName()
                            ));
                            continue;
                        }
                        else if (currentContainer == null) {
                            plugin.getLogger().warning(String.format(
                                    "Id %s is not exist. Class: %s, Method: %s",
                                    annotation.id(), listener.getClass().getName(), method.getName()
                            ));
                            continue;
                        }
                        currentContainer.addHandler(
                                (Class<? extends PancakeItemEvent>) types[0],
                                annotation.source(),
                                (event) -> {
                                    try {
                                        method.invoke(listener, event);
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                        );
                    } else {
                        plugin.getLogger().warning(String.format(
                                "Only 1 arguments method can be registered, Class: %s Method: %s",
                                listener.getClass().getName(), method.getName()
                        ));
                    }
                } catch (Exception e) {
                    plugin.getLogger().log(Level.WARNING, String.format(
                            "Can not subscribe event handler %s:",
                            listener.getClass().getName()), e);
                }
            }
        }
    }

    @Override
    public PancakeItemContainer<?> getItemContainer(String id) {
        return getItems().getOrDefault(id.toLowerCase(Locale.ROOT), null);
    }
}
