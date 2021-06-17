package com.github.jenya705.pancake.enchantment;

import com.github.jenya705.pancake.item.PancakeItemSource;
import com.github.jenya705.pancake.item.event.PancakeItemEvent;
import com.github.jenya705.pancake.util.PancakeUtils;
import io.papermc.paper.enchantments.EnchantmentRarity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.enchantments.EnchantmentTarget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

@Getter
@Setter(AccessLevel.PROTECTED)
public class PancakeEnchantmentContainerImpl<T> implements PancakeEnchantmentContainer<T> {

    private String name;
    private String id;
    private EnchantmentTarget target;
    private EnchantmentRarity rarity;
    private int maxLevel;
    private String[] conflicts;
    private boolean treasure;
    private boolean tradeable;
    private boolean discoverable;
    private T source;
    private Map<Class<? extends PancakeItemEvent>, List<List<BiFunction<PancakeItemEvent, PancakeEnchantmentObject, Void>>>> handlers;

    /**
     * @param source Source object
     * @throws IllegalArgumentException if source does not have {@link PancakeEnchantment} annotation
     */
    public PancakeEnchantmentContainerImpl(T source) {
        Class<?> clazz = source.getClass();
        PancakeEnchantment pancakeEnchantment = clazz.getAnnotation(PancakeEnchantment.class);
        if (pancakeEnchantment == null) throw new IllegalArgumentException("Source does not have PancakeEnchantment annotation");
        setValues(source, pancakeEnchantment);
    }

    /**
     * @param source Source object
     * @param pancakeEnchantment PancakeEnchantment annotation
     */
    public PancakeEnchantmentContainerImpl(T source, PancakeEnchantment pancakeEnchantment) {
        setValues(source, pancakeEnchantment);
    }

    protected void setValues(T source, PancakeEnchantment pancakeEnchantment) {
        setName(pancakeEnchantment.name());
        setId(pancakeEnchantment.id());
        setTarget(pancakeEnchantment.target());
        setRarity(pancakeEnchantment.rarity());
        setMaxLevel(pancakeEnchantment.maxLevel());
        setConflicts(pancakeEnchantment.conflicts());
        setTreasure(pancakeEnchantment.treasure());
        setTradeable(pancakeEnchantment.tradeable());
        setDiscoverable(pancakeEnchantment.discoverable());
        setSource(source);
    }

    @Override
    public boolean isConflict(String enchantmentID) {
        return PancakeUtils.contains(getConflicts(), enchantmentID);
    }

    @Override
    public void invokeEvent(PancakeItemEvent event, PancakeItemSource source, PancakeEnchantmentObject enchantmentObject) {
        if (getHandlers() == null) return;
        List<List<BiFunction<PancakeItemEvent, PancakeEnchantmentObject, Void>>> sourceHandlers = getHandlers().getOrDefault(event.getClass(), null);
        if (sourceHandlers == null) return;
        List<BiFunction<PancakeItemEvent, PancakeEnchantmentObject, Void>> exactHandlers = sourceHandlers.get(source.ordinal());
        if (exactHandlers == null) return;
        exactHandlers.forEach(handler -> handler.apply(event, enchantmentObject));
    }

    @Override
    public void addHandler(Class<? extends PancakeItemEvent> eventClazz, PancakeItemSource source, BiFunction<PancakeItemEvent, PancakeEnchantmentObject, Void> handler) {
        if (getHandlers() == null) setHandlers(new HashMap<>());
        List<List<BiFunction<PancakeItemEvent, PancakeEnchantmentObject, Void>>> sourceHandlers = getHandlers().getOrDefault(eventClazz, null);
        if (sourceHandlers == null) {
            sourceHandlers = new ArrayList<>(PancakeItemSource.values().length);
            for (int i = 0; i < PancakeItemSource.values().length; ++i) sourceHandlers.add(null);
            getHandlers().put(eventClazz, sourceHandlers);
        }
        List<BiFunction<PancakeItemEvent, PancakeEnchantmentObject, Void>> exactHandlers = sourceHandlers.get(source.ordinal());
        if (exactHandlers == null) {
            exactHandlers = new ArrayList<>();
            sourceHandlers.set(source.ordinal(), exactHandlers);
        }
        exactHandlers.add(handler);
    }
}
