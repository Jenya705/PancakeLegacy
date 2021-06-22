package com.github.jenya705.pancake.item.container;

import com.github.jenya705.pancake.Pancake;
import com.github.jenya705.pancake.item.PancakeItem;
import com.github.jenya705.pancake.item.PancakeItemSource;
import com.github.jenya705.pancake.item.event.PancakeItemEvent;
import com.github.jenya705.pancake.item.model.CustomModelItem;
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
    private int additionalEnchantmentCost;
    private int customModelData;

    /**
     * @param source Source object
     * @param customModelData custom model id (by default generated by Pancake)
     * @throws IllegalArgumentException if source doesn't have {@link PancakeItem} annotation
     */
    public PancakeItemContainerImpl(T source, int customModelData) {
        Class<?> clazz = source.getClass();
        PancakeItem pancakeItemAnnotation = clazz.getAnnotation(PancakeItem.class);
        if (pancakeItemAnnotation == null) {
            throw new IllegalArgumentException("Source doesn't have PancakeItem annotation");
        }
        setValues(source, pancakeItemAnnotation, customModelData);
    }

    /**
     * @param source Source object
     * @param pancakeItemAnnotation PancakeItem annotation
     * @param customModelData custom model id (by default generated by Pancake)
     */
    public PancakeItemContainerImpl(T source, PancakeItem pancakeItemAnnotation, int customModelData) {
        setValues(source, pancakeItemAnnotation, customModelData);
    }

    /**
     * @param source Source object
     */
    public PancakeItemContainerImpl(T source) {
        Class<?> clazz = source.getClass();
        PancakeItem pancakeItemAnnotation = clazz.getAnnotation(PancakeItem.class);
        if (pancakeItemAnnotation == null) {
            throw new IllegalArgumentException("Source doesn't have PancakeItem annotation");
        }
        setValues(source, pancakeItemAnnotation);
    }

    /**
     * @param source Source object
     * @param pancakeItemAnnotation pancake item annotation
     */
    public PancakeItemContainerImpl(T source, PancakeItem pancakeItemAnnotation) {
        setValues(source, pancakeItemAnnotation);
    }

    protected void setValues(T source, PancakeItem pancakeItemAnnotation, int customModelData) {
        setCustomModelData(customModelData);
        setName(pancakeItemAnnotation.name());
        setId(pancakeItemAnnotation.id().toLowerCase(Locale.ROOT));
        setAdditionalEnchantmentCost(pancakeItemAnnotation.additionalEnchantmentCost());
        setMaterial(pancakeItemAnnotation.material());
        setSource(source);
    }

    protected void setValues(T source, PancakeItem pancakeItemAnnotation) {
        setName(pancakeItemAnnotation.name());
        setId(pancakeItemAnnotation.id().toLowerCase(Locale.ROOT));
        setAdditionalEnchantmentCost(pancakeItemAnnotation.additionalEnchantmentCost());
        setMaterial(pancakeItemAnnotation.material());
        setSource(source);
        setCustomModelData(getSource() instanceof CustomModelItem ?
                Pancake.getPlugin().getCustomModelDataContainer().getCustomModelData(this) : 0);
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
