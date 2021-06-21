package com.github.jenya705.pancake.enchantment.weight;

import com.github.jenya705.pancake.enchantment.container.BukkitPancakeEnchantmentContainer;
import com.github.jenya705.pancake.enchantment.container.PancakeEnchantmentContainer;
import com.github.jenya705.pancake.item.PancakeItemStack;
import com.github.jenya705.pancake.util.Pair;
import com.github.jenya705.pancake.weight.PancakeWeightUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

@Getter
@Setter(AccessLevel.PROTECTED)
public class PancakeEnchantmentWeightContainerImpl implements PancakeEnchantmentWeightContainer {

    private static final Random localRandom = new Random();

    private List<PancakeEnchantmentWeight> enchantmentContainers;

    public PancakeEnchantmentWeightContainerImpl() {
        setEnchantmentContainers(new ArrayList<>());
    }

    @Override
    public PancakeEnchantmentContainer<?> getEnchantmentContainer(float weightNumber) {
        return ((PancakeEnchantmentWeight) PancakeWeightUtils.getRandomWeight(getEnchantmentContainers(), weightNumber)).getEnchantmentContainer();
    }

    @Override
    public void addEnchantmentContainer(PancakeEnchantmentContainer<?> enchantmentContainer) {
        getEnchantmentContainers().add(new PancakeEnchantmentWeight(enchantmentContainer));
    }

    @Override
    public PancakeEnchantmentContainer<?> getDiscoverableEnchantmentContainer(float weightNumber) {
        return getEnchantmentContainerByPredicate(weightNumber, PancakeEnchantmentContainer::isDiscoverable);
    }

    @Override
    public PancakeEnchantmentContainer<?> getTradeableEnchantmentContainer(float weightNumber) {
        return getEnchantmentContainerByPredicate(weightNumber, PancakeEnchantmentContainer::isTradeable);
    }

    @Override
    public PancakeEnchantmentContainer<?> getTreasureEnchantmentContainer(float weightNumber) {
        return getEnchantmentContainerByPredicate(weightNumber, PancakeEnchantmentContainer::isTreasure);
    }

    @Override
    public PancakeEnchantmentContainer<?> getItemEnchantmentContainer(float weightNumber, PancakeItemStack itemStack) {
        return getEnchantmentContainerByPredicate(weightNumber, enchantmentContainer -> enchantmentContainer.canApply(itemStack));
    }

    @Override
    public PancakeEnchantmentContainer<?> getItemDiscoverableEnchantmentContainer(float weightNumber, PancakeItemStack itemStack) {
        return getEnchantmentContainerByPredicate(weightNumber, enchantmentContainer ->
                enchantmentContainer.isDiscoverable() && enchantmentContainer.canApply(itemStack));
    }

    @Override
    public PancakeEnchantmentContainer<?> getItemTradeableEnchantmentContainer(float weightNumber, PancakeItemStack itemStack) {
        return getEnchantmentContainerByPredicate(weightNumber, enchantmentContainer ->
                enchantmentContainer.isTradeable() && enchantmentContainer.canApply(itemStack));
    }

    @Override
    public PancakeEnchantmentContainer<?> getItemTreasureEnchantmentContainer(float weightNumber, PancakeItemStack itemStack) {
        return getEnchantmentContainerByPredicate(weightNumber, enchantmentContainer ->
                enchantmentContainer.isTreasure() && enchantmentContainer.canApply(itemStack));
    }

    @Override
    public List<PancakeEnchantmentWeightObject> getLineRandomEnchantmentContainersForEnchant(ItemStack itemStack, int clickedContainer, int cost) {
        List<PancakeEnchantmentWeightObject> list = enchantmentManagerB(itemStack, cost);
        if (itemStack.getType() == Material.BOOK && list.size() > 1) {
            list.remove(localRandom.nextInt(list.size()));
        }
        return list;
    }

    protected List<PancakeEnchantmentWeightObject> enchantmentManagerB(ItemStack itemStack, int cost) { // Flag is false
        List<PancakeEnchantmentWeightObject> result = new ArrayList<>();
        PancakeItemStack pancakeItemStack = PancakeItemStack.of(itemStack);
        int additionalLevel = pancakeItemStack.getItemContainer().getAdditionalEnchantmentCost();
        if (additionalLevel <= 0) return result;
        int newerCost = cost + 1 + localRandom.nextInt(additionalLevel / 4 + 1) + localRandom.nextInt(additionalLevel / 4 + 1);
        float f = (localRandom.nextFloat() + localRandom.nextFloat() - 1f) * 0.15f;
        newerCost = Math.max(1, Math.min(Math.round(newerCost + newerCost * f), 2147483647)); // Clamp
        List<PancakeEnchantmentWeightObject> possibleEnchantments = getAllEnchantmentWithForEnchantment(
                newerCost,
                enchantmentContainer ->
                        !enchantmentContainer.isTreasure() &&
                        enchantmentContainer.isDiscoverable() &&
                        (enchantmentContainer.canApply(pancakeItemStack) || itemStack.getType() == Material.BOOK)
        );
        if (!possibleEnchantments.isEmpty()) {
            PancakeEnchantmentWeightObject chosenEnchantment;
            result.add(chosenEnchantment = (PancakeEnchantmentWeightObject) PancakeWeightUtils.getRandomWeight(possibleEnchantments, localRandom.nextFloat()));
            while (localRandom.nextInt(50) <= newerCost) {
                validateConflicts(possibleEnchantments, chosenEnchantment);
                if (possibleEnchantments.isEmpty()) break;
                result.add(chosenEnchantment = (PancakeEnchantmentWeightObject) PancakeWeightUtils.getRandomWeight(possibleEnchantments, localRandom.nextFloat()));
                newerCost/=2;
            }
        }
        return result;
    }

    /**
     *
     * Return Enchantment container which predicate is true
     *
     * @param weightNumber Weight number
     * @param predicate Predicate
     * @return Enchantment container
     */
    public PancakeEnchantmentContainer<?> getEnchantmentContainerByPredicate(float weightNumber, Predicate<PancakeEnchantmentContainer<?>> predicate) {
        return ((PancakeEnchantmentWeight) PancakeWeightUtils
                .getRandomWeightByFilter(
                        getEnchantmentContainers(),
                        weightNumber,
                        (it) -> predicate.test(((PancakeEnchantmentWeight) it).getEnchantmentContainer())))
                .getEnchantmentContainer();
    }

    public List<PancakeEnchantmentWeightObject> getAllEnchantmentWithForEnchantment(int cost, Predicate<PancakeEnchantmentContainer<?>> predicate) {
        List<PancakeEnchantmentWeightObject> result = new ArrayList<>();
        for (PancakeEnchantmentWeight enchantmentWeight: getEnchantmentContainers()) {
            if (!predicate.test(enchantmentWeight.getEnchantmentContainer())) continue;
            for (int j = enchantmentWeight.getEnchantmentContainer().getMaxLevel(); j >= enchantmentWeight.getEnchantmentContainer().getStartLevel(); j--) {
                if (cost >= enchantmentWeight.getEnchantmentContainer().getMinCost(j) && cost <= enchantmentWeight.getEnchantmentContainer().getMaxCost(j)) {
                    result.add(new PancakeEnchantmentWeightObject(enchantmentWeight.getEnchantmentContainer(), j));
                    break;
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

    public static void validateConflicts(List<PancakeEnchantmentWeightObject> array, PancakeEnchantmentWeightObject with) {
        array.removeIf(pancakeEnchantmentContainerIntegerPair ->
                with.getEnchantmentContainer().isConflict(pancakeEnchantmentContainerIntegerPair.getEnchantmentContainer().getId()) ||
                        pancakeEnchantmentContainerIntegerPair.getEnchantmentContainer().isConflict(with.getEnchantmentContainer().getId())
        );
    }


}
