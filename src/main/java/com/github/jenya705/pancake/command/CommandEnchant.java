package com.github.jenya705.pancake.command;

import com.github.jenya705.pancake.Pancake;
import com.github.jenya705.pancake.enchantment.PancakeEnchantmentUtils;
import com.github.jenya705.pancake.enchantment.container.PancakeEnchantmentContainer;
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

public class CommandEnchant implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length < 2) return false;
        String selectorString = args[0];
        String enchantmentString = args[1];
        int level;
        if (args.length > 2) {
            if (NumberUtils.isNumber(args[2])) {
                try { level = Integer.parseInt(args[2]); } catch (Exception e) { level = 1; }
            }
            else {
                level = 1;
            }
        }
        else {
            level = 1;
        }
        PancakeEnchantmentContainer<?> enchantmentContainer = PancakeEnchantmentUtils.getEnchantmentContainer(enchantmentString);
        if (enchantmentContainer == null) {
            sender.sendMessage(ChatColor.RED + String.format("Pancake enchantment with id %s is not exist", enchantmentString));
            return true;
        }
        level = Math.max(level, enchantmentContainer.getMaxLevel());
        if (selectorString.equals("@a")) {
            for (Player player: Bukkit.getOnlinePlayers()) {
                ItemStack generatedItem = PancakeEnchantmentUtils.enchantItem(player.getInventory().getItemInMainHand(), enchantmentContainer, level);
                player.getInventory().setItemInMainHand(generatedItem);
            }
        }
        else if (selectorString.equals("@s")) {
            if (sender instanceof Player) {
                ItemStack generatedItem = PancakeEnchantmentUtils.enchantItem(((Player)sender).getInventory().getItemInMainHand(), enchantmentContainer, level);
                ((Player) sender).getInventory().setItemInMainHand(generatedItem);
            }
            else sender.sendMessage(ChatColor.RED + "Selector @s only for players!");
        }
        else {
            Player player = Bukkit.getPlayer(selectorString);
            if (player == null) {
                sender.sendMessage(ChatColor.RED + String.format("Player %s is not exist", selectorString));
            }
            else {
                ItemStack generatedItem = PancakeEnchantmentUtils.enchantItem(player.getInventory().getItemInMainHand(), enchantmentContainer, level);
                player.getInventory().setItemInMainHand(generatedItem);
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> tab = Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
            tab.add("@a"); tab.add("@s");
            return tab;
        }
        else if (args.length == 2) {
            return Pancake.getPlugin().getRegister().getEnchantmentNames();
        }
        return Collections.emptyList();
    }
}
