package io.taraxacum.finaltech.core.menu;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.core.helper.Icon;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;

/**
 * Should use with {@link AbstractMachine}
 *
 * @author Final_ROOT
 * @since 1.0
 */
public abstract class AbstractMachineMenu extends BlockMenuPreset {
    @Nonnull
    private final SlimefunItem slimefunItem;

    public AbstractMachineMenu(@Nonnull String id, @Nonnull String title, @Nonnull SlimefunItem slimefunItem) {
        super(id, title);
        this.slimefunItem = slimefunItem;
    }

    public AbstractMachineMenu(@Nonnull SlimefunItem slimefunItem) {
        super(slimefunItem.getId(), slimefunItem.getItemName());
        this.slimefunItem = slimefunItem;
    }

    @Override
    public void init() {
        for (int slot : this.getBorder()) {
            this.addCustomItem(slot, Icon.BORDER_ICON);
            this.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int slot : this.getInputBorder()) {
            this.addCustomItem(slot, Icon.INPUT_BORDER_ICON);
            this.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int slot : this.getOutputBorder()) {
            this.addCustomItem(slot, Icon.OUTPUT_BORDER_ICON);
            this.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
        }
    }

    public ChestMenu addCustomItem(int slot, ItemStack item) {
		return item != null ? addItem(slot, new CustomItemStack(item)) : null;
		
	}

	@Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);

        this.updateInventory(blockMenu.toInventory(), block.getLocation());
    }

    @Override
    public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
        return player.hasPermission("slimefun.inventory.bypass") || this.slimefunItem.canUse(player, false) && Slimefun.getProtectionManager().hasPermission(player, block.getLocation(), Interaction.INTERACT_BLOCK);
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(@Nullable ItemTransportFlow itemTransportFlow) {
        if (itemTransportFlow == null) {
            return new int[0];
        }
        return switch (itemTransportFlow) {
            case WITHDRAW -> this.getOutputSlot();
            case INSERT -> this.getInputSlot();
        };
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, @Nullable ItemTransportFlow flow, ItemStack item) {
        return this.getSlotsAccessedByItemTransport(flow);
    }

    protected abstract int[] getBorder();

    protected abstract int[] getInputBorder();

    protected abstract int[] getOutputBorder();

    public abstract int[] getInputSlot();

    public abstract int[] getOutputSlot();

    protected abstract void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location);
}
