package me.fabsi23.timeditems.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.fabsi23.timeditems.config.TimedItemsConfig;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Message {

	public static void sendIfNotVoid(CommandSender sender, String message) {
		if (!(message.trim().equals(""))) {
			message = message.replace("%PREFIX%", TimedItemsConfig.getPluginPrefix());
			sender.sendMessage(translateColors(message));
		}
	}

	public static void sendActionbarAllIfNotVoid(String message) {
		TextComponent component = new TextComponent(translateColors(message));
		for (Player player : Bukkit.getOnlinePlayers())
			sendActionbarIfNotVoid(player, component);
	}

	public static void sendActionbarIfNotVoid(Player player, TextComponent component) {
		if (!component.getText().trim().equals(""))
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
	}

	public static void sendActionbarIfNotVoid(Player player, String message) {
		if (!(message.trim().equals("")))
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(translateColors(message)));

	}

	public static void resetActionbarAll() {
		for (Player player : Bukkit.getOnlinePlayers())
			resetActionbar(player);
	}

	public static void resetActionbar(Player player) {
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""));
	}

	private static String translateColors(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
}
