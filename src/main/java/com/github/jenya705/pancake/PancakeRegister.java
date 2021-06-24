package com.github.jenya705.pancake;

import com.github.jenya705.pancake.enchantment.PancakeEnchantment;
import com.github.jenya705.pancake.enchantment.PancakeEnchantmentEventHandler;
import com.github.jenya705.pancake.enchantment.PancakeEnchantmentListener;
import com.github.jenya705.pancake.enchantment.PancakeEnchantmentObject;
import com.github.jenya705.pancake.enchantment.container.PancakeEnchantmentContainer;
import com.github.jenya705.pancake.enchantment.container.PancakeEnchantmentSelfBuilder;
import com.github.jenya705.pancake.item.PancakeItem;
import com.github.jenya705.pancake.item.PancakeItemEventHandler;
import com.github.jenya705.pancake.item.PancakeItemListener;
import com.github.jenya705.pancake.item.container.PancakeItemContainer;
import com.github.jenya705.pancake.item.event.PancakeItemEvent;
import com.github.jenya705.pancake.item.model.CustomModelArmor;
import com.github.jenya705.pancake.item.model.CustomModelItem;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Register of all pancake objects
 */
public interface PancakeRegister {

    /**
     *
     * Return container of pancake item
     *
     * @param id ID of item
     * @return {@link PancakeItemContainer} of pancake item
     */
    PancakeItemContainer<?> getItemContainer(String id);

    /**
     *
     * Return container of pancake enchantment
     *
     * @param id ID of enchantment
     * @return {@link PancakeEnchantmentContainer} of pancake enchantment
     */
    PancakeEnchantmentContainer<?> getEnchantmentContainer(String id);

    /**
     *
     * Return item names
     *
     * @return Pancake item names
     */
    List<String> getItemNames();

    /**
     *
     * Return enchantment names
     *
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
     * Add to resource pack item model
     *
     * @param customModelItem Custom model item
     * @param itemContainer Item container
     * @param plugin Owner of model
     */
    void registerItemModel(CustomModelItem customModelItem, PancakeItemContainer<?> itemContainer, JavaPlugin plugin);

    /**
     *
     * Add to resource pack armor model
     *
     * @param customModelArmor Custom model armor
     * @param itemContainer Item container
     * @param plugin Owner of model
     */
    void registerArmorModel(CustomModelArmor customModelArmor, PancakeItemContainer<?> itemContainer, JavaPlugin plugin);

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
     * Register item.
     * If item container source is instance of {@link PancakeConfigurable} it will load and save it automatically
     *
     * @param itemContainer Container of item
     * @param plugin Item owner
     */
    void registerItem(PancakeItemContainer<?> itemContainer, JavaPlugin plugin);

    /**
     *
     * Register enchantment.
     * If enchantment is instance of {@link PancakeConfigurable} it will load and save it automatically
     *
     * @param enchantment Builder of enchantment container
     * @param plugin Enchantment owner
     */
    void registerEnchantment(PancakeEnchantmentSelfBuilder enchantment, JavaPlugin plugin);

    /**
     *
     * Register enchantment.
     * If enchantment container source is instance of {@link PancakeConfigurable} it will load and save it automatically
     *
     * @param enchantmentContainer Container of enchantment
     * @param plugin Enchantment owner
     */
    void registerEnchantment(PancakeEnchantmentContainer<?> enchantmentContainer, JavaPlugin plugin);

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
