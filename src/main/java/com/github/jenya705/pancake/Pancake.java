package com.github.jenya705.pancake;

import com.github.jenya705.pancake.data.PancakeDataFactory;
import com.github.jenya705.pancake.data.PancakeDataFactoryImpl;
import com.github.jenya705.pancake.event.armorequip.ArmorEquipMain;
import com.github.jenya705.pancake.item.*;
import com.github.jenya705.pancake.item.event.PancakeItemEvent;
import com.github.jenya705.pancake.item.object.InvisibleCloakItem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@Setter(AccessLevel.PROTECTED)
@Getter
public final class Pancake extends JavaPlugin {

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private static Pancake plugin;

    // Factories
    private PancakeDataFactory dataFactory;

    private PancakeRegister register;

    @Override
    public void onLoad() {
        setPlugin(this);
        setDataFactory(new PancakeDataFactoryImpl());
        setRegister(new PancakeRegisterImpl());
    }

    @Override
    public void onEnable() {
        ArmorEquipMain.enable();
        getRegister().registerAll("com.github.jenya705.pancake", this);
        getServer().getPluginManager().registerEvents(new PancakeBukkitItemListener(), this);
        getDataFolder().mkdir();
    }

    @Override
    public void onDisable() {

    }

    /*
    public <T> void registerPancakeItemListener(PancakeItemContainer<T> container, PancakeItemListener listener) {
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
                        if (currentContainer == null) {
                            getLogger().warning(String.format("Id %s is not exist. Class: %s, Method: %s", annotation.id(), listener.getClass().getName(), method.getName()));
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
                        getLogger().warning(String.format("Only 1 arguments method can be registered, Class: %s Method: %s", listener.getClass().getName(), method.getName()));
                    }
                } catch (Exception e) {
                    getLogger().log(Level.WARNING, String.format("Can not subscribe event handler %s:", listener.getClass().getName()), e);
                }
            }
        }
    }

    public <T> void addItem(T item) {
        PancakeItemContainer<T> itemContainer = new PancakeItemContainerImpl<>(item);
        items.put(itemContainer.getID(), itemContainer);
        if (item instanceof Listener) {
            getServer().getPluginManager().registerEvents((Listener) item, this);
        }
        if (item instanceof PancakeItemListener) {
            registerPancakeItemListener(itemContainer, (PancakeItemListener) item);
        }
    }
     */

}
