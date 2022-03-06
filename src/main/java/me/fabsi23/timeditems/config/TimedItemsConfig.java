package me.fabsi23.timeditems.config;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.fabsi23.timeditems.TimedItems;

public class TimedItemsConfig {

	private static final String CONFIG = "Config.";
	private static final String PREFIX = "Prefix.";
	private static final String MESSAGE = "Messages.";
	private static final String SETTING = "Settings.";

	private static JavaPlugin plugin = TimedItems.getReference();
	private static FileConfiguration cfg = plugin.getConfig();

	public static String getPluginPrefix() {
		return cfg.getString(CONFIG + PREFIX + "plugin-prefix");
	}

	public static String getConsolePrefix() {
		return cfg.getString(CONFIG + PREFIX + "console-prefix");
	}

	public static String getObtainedItem(String item) {
		return cfg.getString(CONFIG + MESSAGE + "obtained-item").replace("%ITEM%", item);
	}

	public static String getNoPermission() {
		return cfg.getString(CONFIG + MESSAGE + "no-permission");
	}

	public static String getConfigReloaded() {
		return cfg.getString(CONFIG + MESSAGE + "reloaded-config");
	}

	public static String getTimerMessage(String time) {
		return cfg.getString(CONFIG + SETTING + "timer-message").replace("%TIME%", time);
	}

	public static int getTimeBetweenBlocksFrom() {
		return cfg.getInt(CONFIG + SETTING + "time-between-blocks-from");
	}

	public static int getTimeBetweenBlocksTo() {
		return cfg.getInt(CONFIG + SETTING + "time-between-blocks-to");
	}

	public static boolean getSameForAll() {
		return cfg.getBoolean(CONFIG + SETTING + "same-for-all");
	}
	
	public static boolean getItemPermissionEnabled() {
		return cfg.getBoolean(CONFIG + SETTING + "enable-item-permission");
	}

	public static List<String> getPossibleItems() {
		return cfg.getStringList(CONFIG + SETTING + "possible-items");
	}

	public static void reload() {
		plugin.reloadConfig();
		cfg = plugin.getConfig();
	}
}
