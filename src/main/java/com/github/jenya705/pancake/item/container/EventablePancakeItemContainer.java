package com.github.jenya705.pancake.item.container;

import com.github.jenya705.pancake.Pancake;
import com.github.jenya705.pancake.item.PancakeItemSource;
import com.github.jenya705.pancake.item.event.PancakeItemEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;

@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class EventablePancakeItemContainer<T> implements PancakeItemContainer<T> {

    private Map<Class<? extends PancakeItemEvent>, List<List<Consumer<PancakeItemEvent>>>> handlers;

    @Override
    public void addHandler(Class<? extends PancakeItemEvent> event, PancakeItemSource source, Consumer<PancakeItemEvent> consumer) {
        if (getHandlers() == null) setHandlers(new HashMap<>());
        List<List<Consumer<PancakeItemEvent>>> sourceHandlers = getHandlers().getOrDefault(event, null);
        if (sourceHandlers == null) {
            sourceHandlers = new ArrayList<>(PancakeItemSource.values().length);
            for (int i = 0; i < PancakeItemSource.values().length; ++i) sourceHandlers.add(null);
            getHandlers().put(event, sourceHandlers);
        }
        List<Consumer<PancakeItemEvent>> exactHandlers = sourceHandlers.get(source.ordinal());
        if (exactHandlers == null) {
            exactHandlers = new ArrayList<>();
            sourceHandlers.set(source.ordinal(), exactHandlers);
        }
        exactHandlers.add(consumer);
    }

    @Override
    public void invokeEvent(PancakeItemEvent event, PancakeItemSource source) {
        if (getHandlers() == null) return;
        List<List<Consumer<PancakeItemEvent>>> sourceConsumers = getHandlers().getOrDefault(event.getClass(), null);
        if (sourceConsumers == null) return;
        List<Consumer<PancakeItemEvent>> consumers = sourceConsumers.get(source.ordinal());
        if (consumers == null) return;
        for (Consumer<PancakeItemEvent> consumer : consumers) {
            try {
                consumer.accept(event);
            } catch (Exception e) {
                Pancake.getPlugin().getLogger().log(Level.SEVERE, "Exception while invoking Pancake Event:", e);
            }
        }
    }
}
