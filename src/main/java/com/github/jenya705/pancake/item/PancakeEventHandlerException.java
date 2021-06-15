package com.github.jenya705.pancake.item;

/**
 * Throws when some event handler throws an exception
 */
public class PancakeEventHandlerException extends RuntimeException {

    public PancakeEventHandlerException(Exception e) {
        super(e);
    }

    public PancakeEventHandlerException(String message) {
        super(message);
    }

}
