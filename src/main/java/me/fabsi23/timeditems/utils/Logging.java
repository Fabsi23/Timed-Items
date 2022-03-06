package me.fabsi23.timeditems.utils;

import org.bukkit.Bukkit;

import me.fabsi23.timeditems.config.TimedItemsConfig;

public class Logging {

	public static void logWarning(String message) {
		Bukkit.getLogger().warning(TimedItemsConfig.getConsolePrefix() + " " + message);
	}

	public static void logInfo(String message) {
		Bukkit.getLogger().info(TimedItemsConfig.getConsolePrefix() + " " + message);
	}
}
