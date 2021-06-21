package com.github.jenya705.pancake;

/**
 * Minecraft legacy message object
 */
public interface PancakeMessage {

    /**
     *
     * Return message with local as content
     *
     * @param local Message with & as Color translator
     * @return {@link PancakeMessage} implementation
     */
    static PancakeMessage of(String local) {
        return new PancakeMessageImpl(local);
    }

    /**
     *
     * Return raw message (Paragraph as color translator)
     *
     * @return Message with Paragraph as Color translator
     */
    String getRaw();

    /**
     *
     * Return local (user-friendly) message (& as color translator)
     *
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
