package com.github.jenya705.pancake.block.container;

import com.github.jenya705.pancake.block.event.PancakeBlockEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class EventablePancakeBlockContainer<T> implements PancakeBlockContainer<T> {

    private Map<Class<? extends PancakeBlockEvent>, List<Consumer<PancakeBlockEvent>>> handlers;

    @Override
    public void addHandler(Class<? extends PancakeBlockEvent> event, Consumer<PancakeBlockEvent> handler) {
        if (getHandlers() == null) setHandlers(new HashMap<>());
        List<Consumer<PancakeBlockEvent>> eventHandlers = getHandlers().getOrDefault(event, null);
        if (eventHandlers == null) getHandlers().put(event, eventHandlers = new ArrayList<>());
        eventHandlers.add(handler);
    }

    @Override
    public void invokeEvent(PancakeBlockEvent event) {
        if (getHandlers() == null) return;
        List<Consumer<PancakeBlockEvent>> eventHandlers = getHandlers().getOrDefault(event.getClass(), null);
        if (eventHandlers == null) return;
        eventHandlers.forEach(it -> it.accept(event));
    }
}
