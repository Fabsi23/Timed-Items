package me.fabsi23.timeditems;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.fabsi23.timeditems.commands.CMDTimedItemsReload;
import me.fabsi23.timeditems.listeners.PlayerJoinListener;
import me.fabsi23.timeditems.tasks.ItemTimer;
import me.fabsi23.timeditems.utils.Logging;

public class TimedItems extends JavaPlugin {

	/**
	 * author: Fabsi23 Date: 20.02.2022 - 20.02.2022 (DMY) Last edited: 06.03.2022
	 */
	
	private static TimedItems reference;
	
	public static ItemTimer timer;

	@Override
	public void onEnable() {
		long current = System.currentTimeMillis();
		TimedItems.reference = this;
		loadConfigurations();
		registerCommands();
		timer = new ItemTimer();
		timer.start();
		registerEvents();
		Logging.logInfo("Plugin activated! Starting took " + (System.currentTimeMillis() - current) + " ms.");
	}

	@Override
	public void onDisable() {
		Logging.logInfo("Plugin deactivated!");
	}

	private void loadConfigurations() {
		saveDefaultConfig();
	}

	private void registerCommands() {
		this.getCommand("timeditemsreload").setExecutor(new CMDTimedItemsReload());
	}
	
	private void registerEvents() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new PlayerJoinListener(timer), this);
	}

	public static TimedItems getReference() {
		return reference;
	}
}