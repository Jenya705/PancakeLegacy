package com.github.jenya705.pancake;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

import java.text.MessageFormat;

@Getter
@Setter
public class PancakeMessageImpl implements PancakeMessage {

    private String message;

    public PancakeMessageImpl(String local) {
        setMessage(local.replaceAll("&", Character.toString(ChatColor.COLOR_CHAR)));
    }

    @Override
    public String getRaw() {
        return getMessage();
    }

    @Override
    public String getLocal() {
        return getMessage().replaceAll(Character.toString(ChatColor.COLOR_CHAR), "&");
    }

    @Override
    public String format(Object... objects) {
        return MessageFormat.format(getRaw(), objects);
    }
}
