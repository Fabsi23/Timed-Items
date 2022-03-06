package me.fabsi23.timeditems.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.fabsi23.timeditems.TimedItems;
import me.fabsi23.timeditems.config.TimedItemsConfig;
import me.fabsi23.timeditems.utils.Message;

public class CMDTimedItemsReload implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("timeditems.reload")) {
			TimedItemsConfig.reload();
			TimedItems.timer.restart();
			Message.sendIfNotVoid(sender, TimedItemsConfig.getConfigReloaded());
		} else {
			Message.sendIfNotVoid(sender, TimedItemsConfig.getNoPermission());
		}
		return true;
	}
}