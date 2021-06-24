package com.github.jenya705.pancake.enchantment.container;

import com.github.jenya705.pancake.enchantment.PancakeEnchantmentObject;
import com.github.jenya705.pancake.item.PancakeItemSource;
import com.github.jenya705.pancake.item.event.PancakeItemEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class EventablePancakeEnchantmentContainer<T> implements PancakeEnchantmentContainer<T> {

    private Map<Class<? extends PancakeItemEvent>, List<List<BiFunction<PancakeItemEvent, PancakeEnchantmentObject, Void>>>> handlers;

    @Override
    public void invokeEvent(PancakeItemEvent event, PancakeItemSource source, PancakeEnchantmentObject enchantmentObject) {
        if (enchantmentObject.getEnchantmentContainer() != this) {
            throw new IllegalArgumentException("This container is not container of given enchantment object");
        }
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
