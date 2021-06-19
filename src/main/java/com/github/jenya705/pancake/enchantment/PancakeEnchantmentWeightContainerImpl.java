package com.github.jenya705.pancake.enchantment;

import com.github.jenya705.pancake.item.PancakeItemStack;
import com.github.jenya705.pancake.util.Pair;
import io.papermc.paper.enchantments.EnchantmentRarity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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
        for (List<PancakeEnchantmentContainer<?>> enchantmentContainers: getEnchantmentContainersByRarity()) {
            for (PancakeEnchantmentContainer<?> listEnchantmentContainer: enchantmentContainers) {
                if (listEnchantmentContainer.getId().equalsIgnoreCase(enchantmentContainer.getId())) {
                    throw new IllegalArgumentException("This enchantment container already set");
                }
            }
        }
        List<PancakeEnchantmentContainer<?>> rarityEnchantmentContainers = getRarityEnchantmentContainers(enchantmentContainer.getRarity());
        rarityEnchantmentContainers.add(enchantmentContainer); // NullPointer avoided
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

    @Override
    public PancakeEnchantmentContainer<?> getItemEnchantmentContainer(int weightNumber, boolean endRandom, PancakeItemStack itemStack) {
        return getEnchantmentContainerByPredicate(weightNumber, endRandom, enchantmentContainer -> enchantmentContainer.canApply(itemStack));
    }

    @Override
    public PancakeEnchantmentContainer<?> getItemDiscoverableEnchantmentContainer(int weightNumber, boolean endRandom, PancakeItemStack itemStack) {
        return getEnchantmentContainerByPredicate(weightNumber, endRandom, enchantmentContainer ->
                enchantmentContainer.isDiscoverable() && enchantmentContainer.canApply(itemStack));
    }

    @Override
    public PancakeEnchantmentContainer<?> getItemTradeableEnchantmentContainer(int weightNumber, boolean endRandom, PancakeItemStack itemStack) {
        return getEnchantmentContainerByPredicate(weightNumber, endRandom, enchantmentContainer ->
                enchantmentContainer.isTradeable() && enchantmentContainer.canApply(itemStack));
    }

    @Override
    public PancakeEnchantmentContainer<?> getItemTreasureEnchantmentContainer(int weightNumber, boolean endRandom, PancakeItemStack itemStack) {
        return getEnchantmentContainerByPredicate(weightNumber, endRandom, enchantmentContainer ->
                enchantmentContainer.isTreasure() && enchantmentContainer.canApply(itemStack));
    }

    @Override
    public List<PancakeEnchantmentContainer<?>> getLineEnchantmentContainers(int count) {
        return getLineEnchantmentContainersByPredicate(count, it -> true);
    }

    @Override
    public List<PancakeEnchantmentContainer<?>> getLineDiscoverableEnchantmentContainers(int count) {
        return getLineEnchantmentContainersByPredicate(count, PancakeEnchantmentContainer::isDiscoverable);
    }

    @Override
    public List<PancakeEnchantmentContainer<?>> getLineTradeableEnchantmentContainers(int count) {
        return getLineEnchantmentContainersByPredicate(count, PancakeEnchantmentContainer::isTradeable);
    }

    @Override
    public List<PancakeEnchantmentContainer<?>> getLineTreasureEnchantmentContainers(int count) {
        return getLineEnchantmentContainersByPredicate(count, PancakeEnchantmentContainer::isTreasure);
    }

    @Override
    public List<PancakeEnchantmentContainer<?>> getLineItemEnchantmentContainers(int count, PancakeItemStack itemStack) {
        return getLineEnchantmentContainersByPredicate(count, enchantmentContainer -> enchantmentContainer.canApply(itemStack));
    }

    @Override
    public List<PancakeEnchantmentContainer<?>> getLineItemDiscoverableEnchantmentContainers(int count, PancakeItemStack itemStack) {
        return getLineEnchantmentContainersByPredicate(count, enchantmentContainer ->
                enchantmentContainer.isDiscoverable() && enchantmentContainer.canApply(itemStack));
    }

    @Override
    public List<PancakeEnchantmentContainer<?>> getLineItemTradeableEnchantmentContainers(int count, PancakeItemStack itemStack) {
        return getLineEnchantmentContainersByPredicate(count, enchantmentContainer ->
                enchantmentContainer.isTradeable() && enchantmentContainer.canApply(itemStack));
    }

    @Override
    public List<PancakeEnchantmentContainer<?>> getLineItemTreasureEnchantmentContainers(int count, PancakeItemStack itemStack) {
        return getLineEnchantmentContainersByPredicate(count, enchantmentContainer ->
                enchantmentContainer.isTreasure() && enchantmentContainer.canApply(itemStack));
    }

    @Override
    public List<Pair<PancakeEnchantmentContainer<?>, Integer>> getLineRandomEnchantmentContainersForEnchant(ItemStack itemStack, int clickedContainer, int cost) {
        List<Pair<PancakeEnchantmentContainer<?>, Integer>> list = enchantmentManagerB(itemStack, cost);
        if (itemStack.getType() == Material.BOOK && list.size() > 1) {
            list.remove(localRandom.nextInt(list.size()));
        }
        return list;
    }

    protected List<Pair<PancakeEnchantmentContainer<?>, Integer>> enchantmentManagerB(ItemStack itemStack, int cost) { // Flag is false
        List<Pair<PancakeEnchantmentContainer<?>, Integer>> result = new ArrayList<>();
        PancakeItemStack pancakeItemStack = PancakeItemStack.of(itemStack);
        int additionalLevel = pancakeItemStack.getItemContainer().getAdditionalEnchantmentLevel();
        if (additionalLevel <= 0) return result;
        int newerCost = cost + 1 + localRandom.nextInt(additionalLevel / 4 + 1) + localRandom.nextInt(additionalLevel / 4 + 1);
        float f = (localRandom.nextFloat() + localRandom.nextFloat() - 1f) * 0.15f;
        newerCost = Math.max(1, Math.min(Math.round(newerCost + newerCost * f), 2147483647)); // Clamp
        List<Pair<PancakeEnchantmentContainer<?>, Integer>> possibleEnchantments = getAllEnchantmentWithForEnchantment(
                newerCost,
                enchantmentContainer ->
                        !enchantmentContainer.isTreasure() &&
                        enchantmentContainer.isDiscoverable() &&
                        (enchantmentContainer.canApply(pancakeItemStack) || itemStack.getType() == Material.BOOK)
        );
        if (!possibleEnchantments.isEmpty()) {
            Pair<PancakeEnchantmentContainer<?>, Integer> chosenEnchantment;
            result.add(chosenEnchantment = getRandomDependOnWeight(localRandom, possibleEnchantments));
            while (localRandom.nextInt(50) <= newerCost) {
                validateConflicts(possibleEnchantments, chosenEnchantment);
                if (possibleEnchantments.isEmpty()) break;
                result.add(chosenEnchantment = getRandomDependOnWeight(localRandom, possibleEnchantments));
                newerCost/=2;
            }
        }
        return result;
    }

    /**
     * @return All registered enchantments count
     */
    public int getAllEnchantmentsCount() {
        return enchantmentContainersByRarity.stream().mapToInt(List::size).sum();
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

    /**
     *
     * @param count Enchantment containers count
     * @param predicate Predicate
     * @return Enchantment containers, which are not conflict each other and predicate is true
     */
    public List<PancakeEnchantmentContainer<?>> getLineEnchantmentContainersByPredicate(int count, Predicate<PancakeEnchantmentContainer<?>> predicate) {
        return getLineEnchantmentContainersByPredicate(count, predicate, false);
    }

    public List<PancakeEnchantmentContainer<?>> getLineEnchantmentContainersByPredicate(int count, Predicate<PancakeEnchantmentContainer<?>> predicate, boolean conflict) {
        List<PancakeEnchantmentContainer<?>> result = new ArrayList<>();
        int[] chosenRarities = new int[EnchantmentRarity.values().length];
        int existingCount = Math.min(count, getAllEnchantmentsCount());
        for (int i = 0; i < existingCount; ++i) {
            int rarityWeight = localRandom.nextInt();
            EnchantmentRarity chosenRarity = getRarityFromWeight(rarityWeight);
            List<PancakeEnchantmentContainer<?>> rarityEnchantmentContainers;
            while (chosenRarities[chosenRarity.ordinal()] >= (rarityEnchantmentContainers = getRarityEnchantmentContainers(chosenRarity)).size()) {
                rarityWeight += chosenRarity.getWeight();
                chosenRarity = getRarityFromWeight(rarityWeight);
            }
            chosenRarities[chosenRarity.ordinal()]++;
            List<PancakeEnchantmentContainer<?>> chooseList = rarityEnchantmentContainers
                    .stream()
                    .filter(predicate)
                    .filter(enchantmentContainer ->
                            !result.contains(enchantmentContainer) &&
                                    result.stream().noneMatch(resultEnchantmentContainer ->
                                            !conflict && (
                                                    resultEnchantmentContainer.isConflict(enchantmentContainer.getId()) ||
                                                    enchantmentContainer.isConflict(resultEnchantmentContainer.getId())
                                            )
                                    )
                    )
                    .collect(Collectors.toList());
            if (chooseList.isEmpty()) return result; // Throwing away (because too deep)
            result.add(chooseList.get(localRandom.nextInt(chooseList.size())));
        }
        return result;
    }

    public List<Pair<PancakeEnchantmentContainer<?>, Integer>> getAllEnchantmentWithForEnchantment(int cost, Predicate<PancakeEnchantmentContainer<?>> predicate) {
        List<List<PancakeEnchantmentContainer<?>>> enchantmentContainersByRarity = getEnchantmentContainersByRarity();
        List<Pair<PancakeEnchantmentContainer<?>, Integer>> result = new ArrayList<>();
        for (List<PancakeEnchantmentContainer<?>> enchantmentContainers: enchantmentContainersByRarity) {
            for (PancakeEnchantmentContainer<?> enchantmentContainer: enchantmentContainers) {
                if (!predicate.test(enchantmentContainer)) continue;
                for (int j = enchantmentContainer.getMaxLevel(); j >= enchantmentContainer.getStartLevel(); j--) {
                    if (cost >= enchantmentContainer.getMinCost(j) && cost <= enchantmentContainer.getMaxCost(j)) {
                        result.add(new Pair<>(enchantmentContainer, j));
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     *
     * Add Bukkit enchantment for given weight container
     *
     * @param weightContainer Weight container
     */
    public static void initializeBukkitEnchantments(PancakeEnchantmentWeightContainer weightContainer) {
        for (BukkitPancakeEnchantmentContainer pancakeEnchantmentContainer : BukkitPancakeEnchantmentContainer.values()) {
            weightContainer.addEnchantmentContainer(pancakeEnchantmentContainer);
        }
    }

    public static Pair<PancakeEnchantmentContainer<?>, Integer> getRandomDependOnWeight(Random random, List<Pair<PancakeEnchantmentContainer<?>, Integer>> array) {
        int sumOfWeights = 0;
        for (Pair<PancakeEnchantmentContainer<?>, Integer> pancakeEnchantmentContainerIntegerPair : array) {
            sumOfWeights += pancakeEnchantmentContainerIntegerPair.left().getRarity().getWeight();
        }
        int randomNumber = random.nextInt(sumOfWeights);
        for (Pair<PancakeEnchantmentContainer<?>, Integer> pancakeEnchantmentContainerIntegerPair: array) {
            randomNumber -= pancakeEnchantmentContainerIntegerPair.left().getRarity().getWeight();
            if (randomNumber < 0) return pancakeEnchantmentContainerIntegerPair;
        }
        return null;
    }

    public static void validateConflicts(List<Pair<PancakeEnchantmentContainer<?>, Integer>> array, Pair<PancakeEnchantmentContainer<?>, Integer> with) {
        array.removeIf(pancakeEnchantmentContainerIntegerPair -> with.left().isConflict(pancakeEnchantmentContainerIntegerPair.left().getId()));
    }


}
