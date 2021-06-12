package com.github.jenya705.pancake;

import com.github.jenya705.pancake.enchantment.*;
import com.github.jenya705.pancake.item.PancakeItem;
import com.github.jenya705.pancake.item.PancakeItemContainer;
import com.github.jenya705.pancake.item.PancakeItemEventHandler;
import com.github.jenya705.pancake.item.PancakeItemListener;
import com.github.jenya705.pancake.item.event.PancakeItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public interface PancakeRegister {

    /**
     * @param id ID of item
     * @return {@link PancakeItemContainer} of pancake item
     */
    PancakeItemContainer<?> getItemContainer(String id);

    /**
     * @param id ID of enchantment
     * @return {@link PancakeEnchantmentContainer} of pancake enchantment
     */
    PancakeEnchantmentContainer<?> getEnchantmentContainer(String id);

    /**
     * @return Pancake item names
     */
    List<String> getItemNames();

    /**
     * @return Pancake enchantment names
     */
    List<String> getEnchantmentNames();

    /**
     *
     * Register all items, enchantments and etc reflective
     *
     * @param packageName Name of package to register
     * @param plugin Owner of package
     */
    void registerAll(String packageName, JavaPlugin plugin);

    /**
     *
     * Register item.
     * If item is instance of {@link PancakeConfigurable} it will load and save it automatically
     *
     * @param item Object with {@link PancakeItem} annotation
     * @param plugin Item owner
     * @throws IllegalArgumentException if item does not have {@link PancakeItem} annotation
     */
    void registerItem(Object item, JavaPlugin plugin);

    /**
     *
     * Register item.
     * If item is instance of {@link PancakeConfigurable} it will load and save it automatically
     *
     * @param item Source object
     * @param annotation Item annotation
     * @param plugin Item owner
     */
    void registerItem(Object item, PancakeItem annotation, JavaPlugin plugin);

    /**
     *
     * Register enchantment.
     * If item is instance of {@link PancakeConfigurable} it will load and save it automatically
     *
     * @param enchantment Object with {@link PancakeEnchantment} annotation
     * @param plugin Enchantment owner
     * @throws IllegalArgumentException if enchantment does not have {@link PancakeEnchantment} annotation
     */
    void registerEnchantment(Object enchantment, JavaPlugin plugin);

    /**
     *
     * Register enchantment.
     * If item is instance of {@link PancakeConfigurable} it will load and save it automatically
     *
     * @param enchantment Source object
     * @param annotation Enchantment annotation
     * @param plugin Enchantment owner
     */
    void registerEnchantment(Object enchantment, PancakeEnchantment annotation, JavaPlugin plugin);

    /**
     *
     * Register all methods with annotation {@link PancakeItemEventHandler} as item event handler
     *
     * @see #registerPancakeItemEventHandler(Consumer, Class, PancakeItemEventHandler, String, JavaPlugin)
     * @param itemListener item listener
     * @param defaultID if some method annotation value id is empty, this value will used to get {@link PancakeItemContainer} or null if none
     * @param plugin ItemListener owner
     */
    void registerPancakeItemListener(PancakeItemListener itemListener,
                                     String defaultID, JavaPlugin plugin);

    /**
     *
     * Register all methods with annotation {@link PancakeEnchantmentEventHandler} as enchantment event handler
     *
     * @see #registerPancakeEnchantmentEventHandler(BiFunction, Class, PancakeEnchantmentEventHandler, String, JavaPlugin)
     * @param enchantmentListener enchantment listener
     * @param defaultID if some method annotation value id is empty, this value will used to get {@link PancakeEnchantmentContainer} or null if none
     * @param plugin EnchantmentListener owner
     */
    void registerPancakeEnchantmentListener(PancakeEnchantmentListener enchantmentListener,
                                            String defaultID, JavaPlugin plugin);

    /**
     *
     * Register function as method with annotation (handlerAnnotation)
     * Faster because not use reflection
     *
     * @see #registerPancakeItemListener(PancakeItemListener, String, JavaPlugin)
     * @param function Function to register
     * @param clazz Item event class
     * @param handlerAnnotation Handler annotation
     * @param defaultID if some method annotation value id is empty, this value will used to get {@link PancakeItemContainer} or null if none
     * @param plugin ItemListener owner
     */
    void registerPancakeItemEventHandler(Consumer<PancakeItemEvent> function, Class<? extends PancakeItemEvent> clazz,
                                         PancakeItemEventHandler handlerAnnotation, String defaultID, JavaPlugin plugin);

    /**
     *
     * Register function as method with annotation (handlerAnnotation)
     * Faster because not use reflection
     *
     * @see #registerPancakeEnchantmentListener(PancakeEnchantmentListener, String, JavaPlugin)
     * @param function Function to register
     * @param clazz Item event class
     * @param handlerAnnotation Handler annotation
     * @param defaultID if some method annotation value id is empty, this value will used to get {@link PancakeEnchantmentContainer} or null if none
     * @param plugin EnchantmentListener owner
     */
    void registerPancakeEnchantmentEventHandler(BiFunction<PancakeItemEvent, PancakeEnchantmentObject, Void> function,
                                                Class<? extends PancakeItemEvent> clazz,
                                                PancakeEnchantmentEventHandler handlerAnnotation, String defaultID, JavaPlugin plugin);

}
