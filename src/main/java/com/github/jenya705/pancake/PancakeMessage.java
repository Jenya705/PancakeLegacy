package com.github.jenya705.pancake;

public interface PancakeMessage {

    static PancakeMessage of(String local) {
        return new PancakeMessageImpl(local);
    }

    String getRaw();

    String getLocal();

    String format(Object... objects);

}
