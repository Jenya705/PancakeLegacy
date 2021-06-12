package com.github.jenya705.pancake;

import com.github.jenya705.pancake.data.PancakeData;
import com.github.jenya705.pancake.data.PancakeDataType;
import com.github.jenya705.pancake.enchantment.*;
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
import java.util.*;
import java.util.logging.Level;

@Getter
@Setter(AccessLevel.PROTECTED)
public class PancakeRegisterImpl implements PancakeRegister {

    private Map<String, PancakeItemContainer<?>> items = new HashMap<>();
    private Map<String, PancakeEnchantmentContainer<?>> enchantments = new HashMap<>();

    @SneakyThrows
    @Override
    public void registerAll(String packageName, JavaPlugin plugin) {
        Reflections packageReflections = new Reflections(packageName);
        Set<Class<?>> itemClasses = packageReflections.getTypesAnnotatedWith(PancakeItem.class);
        for (Class<?> itemClass: itemClasses) {
            PancakeItemContainer<?> itemContainer = new PancakeItemContainerImpl<>(itemClass.getConstructor().newInstance());
            plugin.getLogger().info(String.format("[Pancake] Add item %s", itemContainer.getID()));
            getItems().put(itemContainer.getID(), itemContainer);
        }
        Set<Class<?>> enchantClasses = packageReflections.getTypesAnnotatedWith(PancakeEnchantment.class);
        for (Class<?> enchantClass: enchantClasses) {
            PancakeEnchantmentContainer<?> enchantmentContainer = new PancakeEnchantmentContainerImpl<>(enchantClass.getConstructor().newInstance());
            plugin.getLogger().info(String.format("[Pancake] Add enchantment %s", enchantmentContainer.getId()));
            getEnchantments().put(enchantmentContainer.getId(), enchantmentContainer);
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
    }

    protected void registerOneElement(Object source, String id, PancakeItemContainer<?> itemContainer, PancakeEnchantmentContainer<?> enchantmentContainer, JavaPlugin plugin) {
        if (source instanceof Listener) {
            plugin.getServer().getPluginManager().registerEvents((Listener) source, plugin);
        }
        if (source instanceof PancakeConfigurable) {
            configurable((PancakeConfigurable) source, id, plugin);
        }
        if (source instanceof PancakeItemListener) {
            item(itemContainer, (PancakeItemListener) source, plugin);
        }
        if (source instanceof PancakeEnchantmentListener) {
            enchantment(enchantmentContainer, (PancakeEnchantmentListener) source, plugin);
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
            plugin.getLogger().log(Level.SEVERE, "[Pancake] Configurable Pancake object can not be loaded/saved:", e);
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
                        if (container == null && annotation.id().isEmpty()) {
                            plugin.getLogger().warning(String.format(
                                    "[Pancake] Object is not Pancake item so ID of PancakeItemEventHandler " +
                                            "annotation cannot be empty. Class: %s, Method: %s",
                                    clazz.getName(), method.getName()
                            ));
                            continue;
                        }
                        PancakeItemContainer<?> currentContainer = annotation.id().isEmpty() ? container : PancakeItemUtils.getItemContainer(annotation.id());
                        if (currentContainer == null) {
                            plugin.getLogger().warning(String.format(
                                    "[Pancake] Id %s is not exist. Class: %s, Method: %s",
                                    annotation.id(), listener.getClass().getName(), method.getName()
                            ));
                            continue;
                        }
                        plugin.getLogger().info(String.format(
                                "[Pancake] Adding item handler. HandlerClass: %s, EventClass: %s, Method: %s, ID: %s, Source: %s",
                                listener.getClass(), types[0], method.getName(), annotation.id(), annotation.source()
                        ));
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
                                "[Pancake] Only 1 arguments method can be registered, Class: %s Method: %s",
                                listener.getClass().getName(), method.getName()
                        ));
                    }
                } catch (Exception e) {
                    plugin.getLogger().log(Level.WARNING, String.format(
                            "[Pancake] Can not subscribe event handler %s:",
                            listener.getClass().getName()), e);
                }
            }
        }
    }

    public void enchantment(PancakeEnchantmentContainer<?> container, PancakeEnchantmentListener listener, JavaPlugin plugin) {
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
                        if (container == null && annotation.id().isEmpty()) {
                            plugin.getLogger().warning(String.format(
                                    "[Pancake] Object is not Pancake enchantment so ID of PancakeEnchantmentEventHandler " +
                                            "annotation cannot be empty. Class: %s, Method: %s",
                                    clazz.getName(), method.getName()
                            ));
                            continue;
                        }
                        PancakeEnchantmentContainer<?> currentContainer = annotation.id().isEmpty() ? container : PancakeEnchantmentUtils.getEnchantmentContainer(annotation.id());
                        if (currentContainer == null) {
                            plugin.getLogger().warning(String.format(
                                    "[Pancake] Id %s is not exist. Class: %s, Method: %s",
                                    annotation.id(), listener.getClass().getName(), method.getName()
                            ));
                            continue;
                        }
                        plugin.getLogger().info(String.format(
                                "[Pancake] Adding enchantment handler. HandlerClass: %s, EventClass: %s, Method: %s, ID: %s, Source: %s",
                                listener.getClass(), types[0], method.getName(), annotation.id(), annotation.source()
                        ));
                        currentContainer.addHandler(
                                (Class<? extends PancakeItemEvent>) types[0],
                                annotation.source(),
                                (event, enchantmentObject) -> {
                                    try {
                                        method.invoke(listener, event, enchantmentObject);
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                    return null;
                                }
                        );
                    } else {
                        plugin.getLogger().warning(String.format(
                                "[Pancake] Only 2 arguments method can be registered, Class: %s Method: %s",
                                listener.getClass().getName(), method.getName()
                        ));
                    }
                } catch (Exception e) {
                    plugin.getLogger().log(Level.WARNING, String.format(
                            "[Pancake] Can not subscribe event handler %s:",
                            listener.getClass().getName()), e);
                }
            }
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
    public PancakeEnchantmentContainer<?> getEnchantContainer(String id) {
        return getEnchantments().getOrDefault(id.toLowerCase(Locale.ROOT), null);
    }
}
