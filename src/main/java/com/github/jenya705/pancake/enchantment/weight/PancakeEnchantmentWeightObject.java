package com.github.jenya705.pancake.enchantment.weight;

import com.github.jenya705.pancake.enchantment.container.PancakeEnchantmentContainer;
import lombok.Getter;

@Getter
public class PancakeEnchantmentWeightObject extends PancakeEnchantmentWeight {

    private final int level;

    public PancakeEnchantmentWeightObject(PancakeEnchantmentContainer<?> enchantmentContainer, int level) {
        super(enchantmentContainer);
        this.level = level;
    }

}
