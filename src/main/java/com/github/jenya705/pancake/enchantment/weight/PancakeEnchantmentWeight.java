package com.github.jenya705.pancake.enchantment.weight;

import com.github.jenya705.pancake.enchantment.container.PancakeEnchantmentContainer;
import com.github.jenya705.pancake.weight.PancakeWeight;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PancakeEnchantmentWeight implements PancakeWeight {

    private final PancakeEnchantmentContainer<?> enchantmentContainer;

    @Override
    public float getWeight() {
        return enchantmentContainer.getRarity().getWeight();
    }
}
