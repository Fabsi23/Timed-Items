package me.fabsi23.timeditems.tasks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import me.fabsi23.timeditems.config.TimedItemsConfig;
import me.fabsi23.timeditems.utils.Logging;
import me.fabsi23.timeditems.utils.Message;
import me.fabsi23.timeditems.utils.RandomGenerator;
import me.fabsi23.timeditems.utils.Selectors;
import me.fabsi23.timeditems.utils.Timer;

public class ItemTimer {

	private List<Material> items = new ArrayList<>();
	private Set<Player> hasPermission = new HashSet<>();

	private Timer timer;
	private int runsSinceBlock;
	private int nextBlock;

	public void start() {
		loadItems();
		runsSinceBlock = 0;
		nextBlock = getNextBlockTime();
		refreshPermission();
		if (nextBlock == -1 || items.isEmpty()) {
			Logging.logWarning("Plugin is not enabled because it is disabled in configuration");
			return;
		}
		Runnable run = new Runnable() {
			@Override
			public void run() {
				runsSinceBlock++;
				int secondsLeft = nextBlock - runsSinceBlock;
				if (secondsLeft == 0) {
					runsSinceBlock = 0;
					nextBlock = getNextBlockTime();
					refreshPermission();
					if (TimedItemsConfig.getSameForAll())
						handOutSameItem();
					else
						handOutRandomItemToAll();
				}
				for (Player player : hasPermission)
					Message.sendActionbarIfNotVoid(player, TimedItemsConfig.getTimerMessage("" + secondsLeft));
			}
		};
		timer = new Timer(run);
		timer.startRepeatingTimer(20, 20);
	}

	public void restart() {
		timer.stop();
		start();
	}

	private void loadItems() {
		items = Selectors.getValidItems(TimedItemsConfig.getPossibleItems());
		Logging.logInfo("Loaded " + items.size() + " possible items to give out");
	}

	private int getNextBlockTime() {
		int from = TimedItemsConfig.getTimeBetweenBlocksFrom();
		int to = TimedItemsConfig.getTimeBetweenBlocksTo();
		if (from == 0 || to == 0) {
			return -1;
		}
		return RandomGenerator.randomInt(from, to);
	}

	private void refreshPermission() {
		hasPermission.clear();
		for (Player player : Bukkit.getOnlinePlayers())
			addIfPermission(player);
	}

	public void addIfPermission(Player player) {
		if (!TimedItemsConfig.getItemPermissionEnabled() || player.hasPermission("timeditems.getitem"))
			hasPermission.add(player);
	}

	private void handOutSameItem() {
		ItemStack handOut = new ItemStack(items.get(RandomGenerator.randomInt(0, items.size())));
		handOut = addEffectOrEnchantment(handOut);
		String itemName = handOut.getType().toString().toLowerCase().replace("_", " ");
		for (Player player : hasPermission) {
			if (player.isOnline() && !player.isDead())
				handOutItemToPlayer(handOut, player, itemName);
		}
	}

	private void handOutRandomItemToAll() {
		for (Player player : hasPermission) {
			if (player.isOnline() && !player.isDead()) {
				ItemStack handOut = new ItemStack(items.get(RandomGenerator.randomInt(0, items.size())));
				String itemName = handOut.getType().toString().toLowerCase().replace("_", " ");
				handOut = addEffectOrEnchantment(handOut);
				handOutItemToPlayer(new ItemStack(handOut), player, itemName);
			}
		}
	}

	private void handOutItemToPlayer(ItemStack item, Player player, String itemtext) {
		Collection<ItemStack> noSpace = player.getInventory().addItem(item).values();
		for (ItemStack stack : noSpace) {
			player.getWorld().dropItem(player.getLocation(), stack);
		}
		Message.sendIfNotVoid(player, TimedItemsConfig.getObtainedItem(itemtext));
	}

	// add an effect or enchantment to potions and enchanted books
	private ItemStack addEffectOrEnchantment(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		if (item.getType() == Material.ENCHANTED_BOOK) {
			Enchantment enchantment = Enchantment.values()[RandomGenerator.randomInt(0, Enchantment.values().length)];
			int level = RandomGenerator.randomInt(enchantment.getStartLevel(), enchantment.getMaxLevel() + 1);
			meta.addEnchant(enchantment, level, false);
			item.setItemMeta(meta);
			return item;
		}
		if (meta instanceof PotionMeta) {
			PotionMeta pMeta = (PotionMeta) meta;
			PotionType potion = PotionType.values()[RandomGenerator.randomInt(1, PotionType.values().length)]; // 0 is uncraftable
			pMeta.setBasePotionData(new PotionData(potion));
			item.setItemMeta(pMeta);
			return item;
		}
		return item;
	}
}
