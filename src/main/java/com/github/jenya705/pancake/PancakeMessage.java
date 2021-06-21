package com.github.jenya705.pancake;

/**
 * Minecraft legacy message object
 */
public interface PancakeMessage {

    /**
     * @param local Message with & as Color translator
     * @return {@link PancakeMessage} implementation
     */
    static PancakeMessage of(String local) {
        return new PancakeMessageImpl(local);
    }

    /**
     * @return Message with Paragraph as Color translator
     */
    String getRaw();

    /**
     * @return Message with & as Color translator
     */
    String getLocal();

    /**
     *
     * Format Raw message with {@link java.text.MessageFormat} class
     *
     * @param objects Format objects
     * @return Formatted object
     */
    String format(Object... objects);

}
