package com.github.jenya705.pancake.util;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pair<T, V> {

    private T left;
    private V right;

}
