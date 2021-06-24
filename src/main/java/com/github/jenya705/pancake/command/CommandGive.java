package com.github.jenya705.pancake.command;

import com.github.jenya705.pancake.Pancake;
import com.github.jenya705.pancake.item.PancakeItemUtils;
import com.github.jenya705.pancake.item.container.PancakeItemContainer;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CommandGive implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 2) return false;
        String selectorString = args[0];
        String itemString = args[1];
        int count;
        if (args.length > 2) {
            if (NumberUtils.isNumber(args[2])) {
                try { count = Integer.parseInt(args[2]); } catch (Exception e) { count = 1; }
            }
            else {
                count = 1;
            }
        }
        else {
            count = 1;
        }
        PancakeItemContainer<?> itemContainer = PancakeItemUtils.getSafeItemContainer(itemString);
        if (itemContainer == null) {
            sender.sendMessage(ChatColor.RED + String.format("Pancake item with id %s is not exist", itemString));
            return true;
        }
        ItemStack generatedItem = PancakeItemUtils.generateItemStack(itemContainer);
        generatedItem.setAmount(count);
        if (selectorString.equals("@a")) {
            for (Player player: Bukkit.getOnlinePlayers()) player.getInventory().addItem(generatedItem.clone());
        }
        else if (selectorString.equals("@s")) {
            if (sender instanceof Player) ((Player) sender).getInventory().addItem(generatedItem);
            else sender.sendMessage(ChatColor.RED + "Selector @s only for players!");
        }
        else {
            Player player = Bukkit.getPlayer(selectorString);
            if (player == null) {
                sender.sendMessage(ChatColor.RED + String.format("Player %s is not exist", selectorString));
            }
            else {
                player.getInventory().addItem(generatedItem);
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> tab = Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
            tab.add("@a"); tab.add("@s");
            return tab;
        }
        else if (args.length == 2) {
            return Pancake.getPlugin().getRegister().getItemNames();
        }
        return Collections.emptyList();
    }
}
