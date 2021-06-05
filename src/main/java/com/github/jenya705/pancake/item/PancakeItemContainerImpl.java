package com.github.jenya705.pancake.item;

import com.github.jenya705.pancake.Pancake;
import com.github.jenya705.pancake.item.event.PancakeItemEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Level;

@Setter(AccessLevel.PROTECTED)
@Getter
public class PancakeItemContainerImpl<T> implements PancakeItemContainer<T> {

    private String name;
    private Material material;
    private Map<Class<? extends PancakeItemEvent>, List<List<Consumer<PancakeItemEvent>>>> handlers;
    private T source;
    private String id;

    /**
     * @param source Source object
     * @throws IllegalArgumentException if source doesn't have {@link PancakeItem} annotation
     */
    public PancakeItemContainerImpl(T source) {
        Class<?> clazz = source.getClass();
        PancakeItem pancakeItemAnnotation = clazz.getAnnotation(PancakeItem.class);
        if (pancakeItemAnnotation == null) {
            throw new IllegalArgumentException("Source doesn't have PancakeItem annotation");
        }
        setName(pancakeItemAnnotation.name());
        setId(pancakeItemAnnotation.id().toLowerCase(Locale.ROOT));
        setMaterial(pancakeItemAnnotation.material());
        setHandlers(new HashMap<>());
        setSource(source);
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PancakeItemContainerImpl<?> that = (PancakeItemContainerImpl<?>) o;
        return name.equals(that.name) && material == that.material && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public void addHandler(Class<? extends PancakeItemEvent> event, PancakeItemSource source, Consumer<PancakeItemEvent> consumer) {
        if (getHandlers().containsKey(event)) {
            List<Consumer<PancakeItemEvent>> consumers = getHandlers().get(event).get(source.ordinal());
            if (consumers == null) {
                consumers = new ArrayList<>();
                getHandlers().get(event).set(source.ordinal(), consumers);
            }
            consumers.add(consumer);
        }
        else {
            List<List<Consumer<PancakeItemEvent>>> sourceConsumer = new ArrayList<>(PancakeItemSource.values().length);
            for (int i = 0; i < PancakeItemSource.values().length; ++i) sourceConsumer.add(null);
            List<Consumer<PancakeItemEvent>> consumers = new ArrayList<>();
            consumers.add(consumer);
            sourceConsumer.set(source.ordinal(), consumers);
            getHandlers().put(event, sourceConsumer);
        }
    }

    @Override
    public void invokeEvent(PancakeItemEvent event, PancakeItemSource source) {
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

