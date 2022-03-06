package me.fabsi23.timeditems.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public class Selectors {

	public static List<Material> getValidItems(List<String> items) {
		boolean all = items.contains("ALL");
		List<Material> validItems = new ArrayList<>();
		List<String> contain = new ArrayList<>();
		for (String typeStr : items) {
			if (typeStr.startsWith("c")) {
				contain.add(typeStr.substring(1));
			}
		}
		boolean add;
		String typeStr;
		for (Material type : Material.values()) {
			if (!type.isItem()) // only consider obtainable items
				continue;
			typeStr = type.toString();
			if (all) {
				if (items.contains(typeStr))
					continue;
				add = true;
				for (String con : contain) {
					if (typeStr.contains(con)) {
						add = false;
						break;
					}
				}
				if (add)
					validItems.add(type);
			} else {
				if (items.contains(typeStr)) {
					validItems.add(type);
					continue;
				}
				for (String con : contain) {
					if (typeStr.contains(con)) {
						validItems.add(type);
						break;
					}
				}
			}
		}
		return validItems;
	}

}
