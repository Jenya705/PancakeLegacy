package com.github.jenya705.pancake.enchantment;

import com.github.jenya705.pancake.item.PancakeItemStack;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
public class PancakeEnchantmentObjectImpl implements PancakeEnchantmentObject {

    private PancakeEnchantmentContainer<?> enchantmentContainer;
    private int level;
    private PancakeItemStack itemStack;

}

