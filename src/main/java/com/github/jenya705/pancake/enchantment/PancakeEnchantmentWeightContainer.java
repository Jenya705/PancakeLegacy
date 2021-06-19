package com.github.jenya705.pancake.enchantment;

import com.github.jenya705.pancake.item.PancakeItemStack;
import com.github.jenya705.pancake.util.Pair;
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
    PancakeEnchantmentContainer<?> getEnchantmentContainer(int weightNumber);

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
     * @param endRandom if true it will choose random enchantment container otherwise it will module number
     * @return {@link PancakeEnchantmentContainer} with given weight number
     */
    PancakeEnchantmentContainer<?> getDiscoverableEnchantmentContainer(int weightNumber, boolean endRandom);

    /**
     *
     * Return enchantment container which is tradeable (can be traded)
     *
     * @param weightNumber Weight number
     * @param endRandom if true it will choose random enchantment container otherwise it will module number
     * @return {@link PancakeEnchantmentContainer} with given weight number
     */
    PancakeEnchantmentContainer<?> getTradeableEnchantmentContainer(int weightNumber, boolean endRandom);

    /**
     *
     * Return enchantment container which is treasure
     *
     * @param weightNumber Weight number
     * @param endRandom if true it will choose random enchantment container otherwise it will module number
     * @return {@link PancakeEnchantmentContainer} with given weight number
     */
    PancakeEnchantmentContainer<?> getTreasureEnchantmentContainer(int weightNumber, boolean endRandom);

    /**
     *
     * Return enchantment container which is can be applied for item
     *
     * @param weightNumber Weight number
     * @param endRandom if true it will choose random enchantment container otherwise it will module number
     * @param itemStack Pancake item stack
     * @return {@link PancakeEnchantmentContainer} with given weight number
     */
    PancakeEnchantmentContainer<?> getItemEnchantmentContainer(int weightNumber, boolean endRandom, PancakeItemStack itemStack);

    /**
     *
     * Return enchantment container which is discoverable and can be applied for item
     *
     * @param weightNumber Weight number
     * @param endRandom if true it will choose random enchantment container otherwise it will module number
     * @param itemStack Pancake item stack
     * @return {@link PancakeEnchantmentContainer} with given weight number
     */
    PancakeEnchantmentContainer<?> getItemDiscoverableEnchantmentContainer(int weightNumber, boolean endRandom, PancakeItemStack itemStack);

    /**
     *
     * Return enchantment container which is tradeable and can be applied for item
     *
     * @param weightNumber Weight number
     * @param endRandom if true it will choose random enchantment container otherwise it will module number
     * @param itemStack Pancake item stack
     * @return {@link PancakeEnchantmentContainer} with given weight number
     */
    PancakeEnchantmentContainer<?> getItemTradeableEnchantmentContainer(int weightNumber, boolean endRandom, PancakeItemStack itemStack);

    /**
     *
     * Return enchantment container which is treasure and can be applied for item
     *
     * @param weightNumber Weight number
     * @param endRandom if true it will choose random enchantment container otherwise it will module number
     * @param itemStack Pancake item stack
     * @return {@link PancakeEnchantmentContainer} with given weight number
     */
    PancakeEnchantmentContainer<?> getItemTreasureEnchantmentContainer(int weightNumber, boolean endRandom, PancakeItemStack itemStack);

    /**
     *
     * Return enchantment containers, which are not conflicts with each other, using Random
     *
     * @param count Enchantment containers count
     * @return Enchantment containers, which are not conflicts with each other
     */
    List<PancakeEnchantmentContainer<?>> getLineEnchantmentContainers(int count);

    /**
     *
     * Return enchantment containers, which is discoverable and not conflicts with each other, using Random
     *
     * @param count Enchantment containers count
     * @return Enchantment containers, which are discoverable and not conflicts with each other
     */
    List<PancakeEnchantmentContainer<?>> getLineDiscoverableEnchantmentContainers(int count);

    /**
     *
     * Return enchantment containers, which are tradeable and not conflicts with each other, using Random
     *
     * @param count Enchantment containers count
     * @return Enchantment container, which are tradeable and not conflicts with each other, using Random
     */
    List<PancakeEnchantmentContainer<?>> getLineTradeableEnchantmentContainers(int count);

    /**
     *
     * Return enchantment containers, which are treasure and not conflicts with each other, using Random
     *
     * @param count Enchantment containers count
     * @return Enchantment container, which are treasure and not conflicts with each other
     */
    List<PancakeEnchantmentContainer<?>> getLineTreasureEnchantmentContainers(int count);

    /**
     *
     * Return enchantment container, which are can be applied to item, using Random
     *
     * @param count Enchantment containers count
     * @param itemStack Pancake item stack
     * @return Enchantment containers, which are can be applied to item
     */
    List<PancakeEnchantmentContainer<?>> getLineItemEnchantmentContainers(int count, PancakeItemStack itemStack);

    /**
     *
     * Return enchantment container, which are discoverable and can be applied to item, using Random
     *
     * @param count Enchantment containers count
     * @param itemStack Pancake item stack
     * @return Enchantment containers, which are discoverable and can be applied to item
     */
    List<PancakeEnchantmentContainer<?>> getLineItemDiscoverableEnchantmentContainers(int count, PancakeItemStack itemStack);

    /**
     *
     * Return enchantment container, which are tradeable and can be applied to item, using Random
     *
     * @param count Enchantment containers count
     * @param itemStack Pancake item stack
     * @return Enchantment containers, which are tradeable and can be applied to item
     */
    List<PancakeEnchantmentContainer<?>> getLineItemTradeableEnchantmentContainers(int count, PancakeItemStack itemStack);

    /**
     *
     * Return enchantment container, which are treasure and can be applied to item, using Random
     *
     * @param count Enchantment containers count
     * @param itemStack Pancake item stack
     * @return Enchantment containers, which are treasure and can be applied to item
     */
    List<PancakeEnchantmentContainer<?>> getLineItemTreasureEnchantmentContainers(int count, PancakeItemStack itemStack);

    /**
     *
     * By default pancake implementation, returns enchantments using minecraft random algorithm
     *
     * @param itemStack Item stack to enchant
     * @param clickedContainer Clicked container (0 - 2)
     * @param cost Cost of enchant
     * @return Enchantments
     */
    List<Pair<PancakeEnchantmentContainer<?>, Integer>> getLineRandomEnchantmentContainersForEnchant(ItemStack itemStack, int clickedContainer, int cost);


}
