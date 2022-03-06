package me.fabsi23.timeditems.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.fabsi23.timeditems.tasks.ItemTimer;

public class PlayerJoinListener implements Listener {

	private ItemTimer timer;
	
	public PlayerJoinListener(ItemTimer timer) {
		this.timer = timer;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		timer.addIfPermission(e.getPlayer());
	}
	
}
