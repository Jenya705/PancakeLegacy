package com.github.jenya705.pancake.enchantment;

import com.github.jenya705.pancake.item.PancakeItemContainer;
import io.papermc.paper.enchantments.EnchantmentRarity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
@Setter(AccessLevel.PROTECTED)
public class PancakeEnchantmentWeightContainerImpl implements PancakeEnchantmentWeightContainer {

    private static final Random localRandom = new Random();

    private List<List<PancakeEnchantmentContainer<?>>> enchantmentContainersByRarity;

    public PancakeEnchantmentWeightContainerImpl() {
        setEnchantmentContainersByRarity(new ArrayList<>(EnchantmentRarity.values().length));
        for (int i = 0; i < EnchantmentRarity.values().length; ++i) {
            getEnchantmentContainersByRarity().add(new ArrayList<>());
        }
    }

    @Override
    public PancakeEnchantmentContainer<?> getEnchantmentContainer(int weightNumber) {
        int weightsSum = getAllWeightsCount();
        int localWeightNumber = weightNumber % weightsSum;
        for (EnchantmentRarity rarity: EnchantmentRarity.values()) {
            List<PancakeEnchantmentContainer<?>> rarityEnchantmentContainers = getRarityEnchantmentContainers(rarity);
            if (localWeightNumber < rarityEnchantmentContainers.size() * rarity.getWeight()) {
                return rarityEnchantmentContainers.get(localWeightNumber / rarity.getWeight());
            }
            localWeightNumber -= rarityEnchantmentContainers.size() * rarity.getWeight();
        }
        return null;
    }

    @Override
    public void addEnchantmentContainer(PancakeEnchantmentContainer<?> enchantmentContainer) {
        getRarityEnchantmentContainers(enchantmentContainer.getRarity()).add(enchantmentContainer); // NullPointer avoided
    }

    @Override
    public PancakeEnchantmentContainer<?> getDiscoverableEnchantmentContainer(int weightNumber, boolean endRandom) {
        return getEnchantmentContainerByPredicate(weightNumber, endRandom, PancakeEnchantmentContainer::isDiscoverable);
    }

    @Override
    public PancakeEnchantmentContainer<?> getTradeableEnchantmentContainer(int weightNumber, boolean endRandom) {
        return getEnchantmentContainerByPredicate(weightNumber, endRandom, PancakeEnchantmentContainer::isTradeable);
    }

    @Override
    public PancakeEnchantmentContainer<?> getTreasureEnchantmentContainer(int weightNumber, boolean endRandom) {
        return getEnchantmentContainerByPredicate(weightNumber, endRandom, PancakeEnchantmentContainer::isTreasure);
    }

    /**
     * @return All registered enchantments count
     */
    public int getAllEnchantmentsCount() {
        return enchantmentContainersByRarity.stream().map(List::size).mapToInt(a -> a).sum();
    }

    /**
     * @return All rarity weight sum
     */
    public int getAllRarityWeightsSum() {
        return Arrays.stream(EnchantmentRarity.values()).mapToInt(EnchantmentRarity::getWeight).sum();
    }

    /**
     * @return Return summary weights count
     */
    public int getAllWeightsCount() {
        return Arrays.stream(EnchantmentRarity.values())
                .mapToInt(rarity -> getRarityEnchantmentContainers(rarity).size() * rarity.getWeight())
                .sum();
    }

    /**
     * @param weightNumber Weight number
     * @return Enchantment rarity of this weight number
     */
    public EnchantmentRarity getRarityFromWeight(int weightNumber) {
        int rarityWeightsCount = getAllRarityWeightsSum();
        int localizedWeightNumber = weightNumber % rarityWeightsCount;
        for (EnchantmentRarity rarity: EnchantmentRarity.values()) {
            if (localizedWeightNumber < rarity.getWeight()) return rarity;
            localizedWeightNumber -= rarity.getWeight();
        }
        return null;
    }

    public List<PancakeEnchantmentContainer<?>> getRarityEnchantmentContainers(EnchantmentRarity rarity) {
        return getEnchantmentContainersByRarity().get(rarity.ordinal());
    }

    /**
     *
     * Return Enchantment container which predicate is true
     *
     * @param weightNumber Weight number
     * @param endRandom if true it will choose random enchantment container otherwise it will module number
     * @param predicate Predicate
     * @return Enchantment container
     */
    public PancakeEnchantmentContainer<?> getEnchantmentContainerByPredicate(int weightNumber, boolean endRandom, Predicate<PancakeEnchantmentContainer<?>> predicate) {
        EnchantmentRarity rarity = getRarityFromWeight(weightNumber);
        List<PancakeEnchantmentContainer<?>> rarityEnchantmentContainers = getRarityEnchantmentContainers(rarity);
        List<PancakeEnchantmentContainer<?>> discoverableEnchantmentContainers =
                rarityEnchantmentContainers.stream()
                        .filter(predicate)
                        .collect(Collectors.toList());
        return discoverableEnchantmentContainers.isEmpty() ? null :
                discoverableEnchantmentContainers.get(
                        endRandom ?
                                localRandom.nextInt(discoverableEnchantmentContainers.size()) :
                                weightNumber % discoverableEnchantmentContainers.size()
                )
                ;
    }

}
