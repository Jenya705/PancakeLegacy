package com.github.jenya705.pancake.item.object;

import com.github.jenya705.pancake.item.PancakeItem;
import com.github.jenya705.pancake.item.PancakeItemEventHandler;
import com.github.jenya705.pancake.item.PancakeItemListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@PancakeItem(
        name = "Magic Carrot",
        id = "pancake:magic_carrot",
        material = Material.CARROT
)
public class MagicCarrotItem implements Listener, PancakeItemListener {

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {

    }

}
