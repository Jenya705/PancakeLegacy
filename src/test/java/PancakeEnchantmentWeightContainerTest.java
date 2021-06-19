import com.github.jenya705.pancake.enchantment.*;
import io.papermc.paper.enchantments.EnchantmentRarity;
import org.bukkit.enchantments.EnchantmentTarget;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PancakeEnchantmentWeightContainerTest {

    private static final PancakeEnchantmentContainer<?>[] testEnchantments = new PancakeEnchantmentContainer[] {
            new PancakeEnchantmentContainerImpl<Void>(null, PancakeEnchantmentBuilder
                    .builder()
                    .name("Tradeable")
                    .target(EnchantmentTarget.TOOL)
                    .id("pancake:tradeable")
                    .tradeable(true)
                    .discoverable(false)
                    .treasure(false)
                    .build()
            ),
            new PancakeEnchantmentContainerImpl<Void>(null, PancakeEnchantmentBuilder
                    .builder()
                    .name("Discoverable")
                    .target(EnchantmentTarget.TOOL)
                    .id("pancake:discoverable")
                    .tradeable(false)
                    .discoverable(true)
                    .treasure(false)
                    .build()
            ),
            new PancakeEnchantmentContainerImpl<Void>(null, PancakeEnchantmentBuilder
                    .builder()
                    .name("Treasure")
                    .target(EnchantmentTarget.TOOL)
                    .id("pancake:treasure")
                    .tradeable(false)
                    .discoverable(false)
                    .treasure(true)
                    .build()
            ),
            new PancakeEnchantmentContainerImpl<Void>(null, PancakeEnchantmentBuilder
                    .builder()
                    .name("All")
                    .target(EnchantmentTarget.TOOL)
                    .id("pancake:all")
                    .build()
            ),
            new PancakeEnchantmentContainerImpl<Void>(null, PancakeEnchantmentBuilder
                    .builder()
                    .name("Conflict")
                    .target(EnchantmentTarget.TOOL)
                    .id("pancake:conflict")
                    .conflict("pancake:all")
                    .conflict("pancake:treasure")
                    .build()
            ),
            new PancakeEnchantmentContainerImpl<Void>(null, PancakeEnchantmentBuilder
                    .builder()
                    .name("Very rare")
                    .id("pancake:very_rare")
                    .rarity(EnchantmentRarity.VERY_RARE)
                    .target(EnchantmentTarget.TOOL)
                    .build()
            )
    };

    private static final PancakeEnchantmentWeightContainer weightContainer = new PancakeEnchantmentWeightContainerImpl();
    static {
        for (PancakeEnchantmentContainer<?> enchantmentContainer : testEnchantments) {
            weightContainer.addEnchantmentContainer(enchantmentContainer);
        }
    }

    @Test
    public void defaultLineTest() {
        List<PancakeEnchantmentContainer<?>> lineContainers = weightContainer.getLineEnchantmentContainers(2);
        Assertions.assertFalse(
                lineContainers.stream()
                        .anyMatch(enchantmentContainer ->
                                lineContainers.stream()
                                        .anyMatch(anotherContainer ->
                                                enchantmentContainer.isConflict(anotherContainer.getId()) ||
                                                        anotherContainer.isConflict(enchantmentContainer.getId())
                                        )
                )
        );
    }

    @Test
    public void typeLineTest() {
        List<PancakeEnchantmentContainer<?>> tradeableLineContainer = weightContainer.getLineTradeableEnchantmentContainers(2);
        Assertions.assertFalse(
                tradeableLineContainer.stream()
                        .anyMatch(enchantmentContainer ->
                                tradeableLineContainer.stream()
                                        .anyMatch(anotherContainer ->
                                                enchantmentContainer.isConflict(anotherContainer.getId()) ||
                                                        anotherContainer.isConflict(enchantmentContainer.getId())
                                        )
                        )
        );
        Assertions.assertTrue(
                tradeableLineContainer.stream().allMatch(PancakeEnchantmentContainer::isTradeable)
        );
    }

}
