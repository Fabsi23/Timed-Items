package me.fabsi23.timeditems.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public class Selectors {

	public static List<Material> getValidItems(List<String> items) {
		boolean all = items.contains("ALL");
		List<Material> validMaterials = new ArrayList<>();
		List<String> contain = getContainList(items);
		for (Material mat : Material.values()) {
			if (!mat.isItem())
				continue;
			String materialname = mat.toString();
			if (isValid(materialname, items, contain, all))
				validMaterials.add(mat);
		}
		return validMaterials;
	}
	
	private static List<String> getContainList(List<String> valid) {
		List<String> contain = new ArrayList<>();
		for (String validStr : valid) {
			if (validStr.startsWith("c"))
				contain.add(validStr.substring(1));
		}
		return contain;
	}

	private static boolean isInAnyContainClause(String str, List<String> contain) {
		for (String con : contain) {
			if (str.contains(con))
				return true;
		}
		return false;
	}

	private static boolean isValid(String str, List<String> valid, List<String> contain, boolean all) {
		if (all) {
			if (!valid.contains(str) && !isInAnyContainClause(str, contain))
				return true;
		} else {
			if (valid.contains(str) || isInAnyContainClause(str, contain))
				return true;
		}
		return false;
	}
}
