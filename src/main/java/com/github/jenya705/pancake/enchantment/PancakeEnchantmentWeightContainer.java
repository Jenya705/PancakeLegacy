package com.github.jenya705.pancake.enchantment;

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

}
