package io.taraxacum.common.api;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;

public class CustomMenu extends ChestMenu{
	

	public CustomMenu(String title) {
		super(title);
	}


	public ChestMenu addCustomItem(int slot, ItemStack item) {
		return item != null ? addItem(slot, new CustomItemStack(item)) : null;
    }

}
