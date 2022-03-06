package me.fabsi23.timeditems.utils;

import org.bukkit.Bukkit;

import me.fabsi23.timeditems.TimedItems;

public class Timer {

	private static TimedItems plugin = TimedItems.getReference();

	private boolean running = false;
	private int scheduler;

	private Runnable runnable;

	public Timer(Runnable runnable) {
		this.runnable = runnable;
	}

	public boolean startDelayedTimer(long ticks) {
		if (running)
			return false;
		running = true;
		scheduler = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, runnable, ticks);
		return true;
	}

	public boolean startRepeatingTimer(long delay, long interval) {
		if (running)
			return false;
		running = true;
		scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, runnable, delay, interval);
		return true;
	}

	public void stop() {
		if (running) {
			Bukkit.getScheduler().cancelTask(scheduler);
			running = false;
		}
	}

	public boolean isRunning() {
		return running;
	}
}
