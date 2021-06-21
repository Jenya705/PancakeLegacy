package com.github.jenya705.pancake.enchantment.weight;

import com.github.jenya705.pancake.enchantment.container.PancakeEnchantmentContainer;
import com.github.jenya705.pancake.item.PancakeItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Container of enchantments weights (or Manager of weights)
 */
public interface PancakeEnchantmentWeightContainer {

    /**
     *
     * Return enchantment container from all enchantments registered
     *
     * @param weightNumber Weight number
     * @return {@link PancakeEnchantmentContainer} with given weight number
     */
    PancakeEnchantmentContainer<?> getEnchantmentContainer(float weightNumber);

    /**
     *
     * Add enchantment container to weight container
     *
     * @throws IllegalArgumentException if this id is already set
     * @param enchantmentContainer Enchantment container which will be added
     */
    void addEnchantmentContainer(PancakeEnchantmentContainer<?> enchantmentContainer);

    /**
     *
     * Return enchantment container which is discoverable (can be enchanted)
     *
     * @param weightNumber Weight number
     * @return {@link PancakeEnchantmentContainer} with given weight number
     */
    PancakeEnchantmentContainer<?> getDiscoverableEnchantmentContainer(float weightNumber);

    /**
     *
     * Return enchantment container which is tradeable (can be traded)
     *
     * @param weightNumber Weight number
     * @return {@link PancakeEnchantmentContainer} with given weight number
     */
    PancakeEnchantmentContainer<?> getTradeableEnchantmentContainer(float weightNumber);

    /**
     *
     * Return enchantment container which is treasure
     *
     * @param weightNumber Weight number
     * @return {@link PancakeEnchantmentContainer} with given weight number
     */
    PancakeEnchantmentContainer<?> getTreasureEnchantmentContainer(float weightNumber);

    /**
     *
     * Return enchantment container which is can be applied for item
     *
     * @param weightNumber Weight number
     * @param itemStack Pancake item stack
     * @return {@link PancakeEnchantmentContainer} with given weight number
     */
    PancakeEnchantmentContainer<?> getItemEnchantmentContainer(float weightNumber, PancakeItemStack itemStack);

    /**
     *
     * Return enchantment container which is discoverable and can be applied for item
     *
     * @param weightNumber Weight number
     * @param itemStack Pancake item stack
     * @return {@link PancakeEnchantmentContainer} with given weight number
     */
    PancakeEnchantmentContainer<?> getItemDiscoverableEnchantmentContainer(float weightNumber, PancakeItemStack itemStack);

    /**
     *
     * Return enchantment container which is tradeable and can be applied for item
     *
     * @param weightNumber Weight number
     * @param itemStack Pancake item stack
     * @return {@link PancakeEnchantmentContainer} with given weight number
     */
    PancakeEnchantmentContainer<?> getItemTradeableEnchantmentContainer(float weightNumber, PancakeItemStack itemStack);

    /**
     *
     * Return enchantment container which is treasure and can be applied for item
     *
     * @param weightNumber Weight number
     * @param itemStack Pancake item stack
     * @return {@link PancakeEnchantmentContainer} with given weight number
     */
    PancakeEnchantmentContainer<?> getItemTreasureEnchantmentContainer(float weightNumber, PancakeItemStack itemStack);
    /**
     *
     *
     * Return enchantment which will be item enchanted when item enchanted in enchanting table
     *
     * @param itemStack Item stack to enchant
     * @param clickedContainer Clicked container (0 - 2)
     * @param cost Cost of enchant
     * @return Enchantments
     */
    List<PancakeEnchantmentWeightObject> getLineRandomEnchantmentContainersForEnchant(ItemStack itemStack, int clickedContainer, int cost);


}
