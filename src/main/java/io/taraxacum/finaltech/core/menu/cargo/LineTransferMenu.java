package io.taraxacum.finaltech.core.menu.cargo;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.core.helper.*;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;
 
public class LineTransferMenu extends AbstractMachineMenu {
    public static final int[] ITEM_MATCH = new int[]{6, 7, 8, 15, 16, 17, 24, 25, 26};
    private static final int[] BORDER = new int[]{5, 9, 10, 11, 14, 18, 19, 20, 21, 22, 23};
    private static final int[] INPUT_BORDER = new int[0];
    private static final int[] OUTPUT_BORDER = new int[0];
    private static final int[] INPUT_SLOT = new int[]{36, 37, 38, 39, 40, 41, 42, 43, 44};
    private static final int[] OUTPUT_SLOT = new int[]{45, 46, 47, 48, 49, 50, 51, 52, 53};
    private static final int[] SPECIAL_BORDER = new int[]{27, 28, 29, 30, 31, 32, 33, 34, 35};
    private static final int BLOCK_SEARCH_MODE_SLOT = 0;
    private static final int BLOCK_SEARCH_ORDER_SLOT = 1;
    private static final int CARGO_ORDER_SLOT = 2;
    private static final int BLOCK_SEARCH_CYCLE_SLOT = 3;
    private static final int BLOCK_SEARCH_SELF_SLOT = 4;
    //    private static final int CARGO_NUMBER_SUB_SLOT = 9;
//    private static final int CARGO_NUMBER_SLOT = 10;
//    private static final int CARGO_NUMBER_ADD_SLOT = 11;
    private static final int CARGO_MODE_SLOT = 12;

//    private static final int INPUT_SLOT_SEARCH_SIZE_SLOT = 18;
//    private static final int INPUT_SLOT_SEARCH_ORDER_SLOT = 19;
//    private static final int CARGO_LIMIT_SLOT = 20;
//    private static final int OUTPUT_SLOT_SEARCH_SIZE_SLOT = 21;
//    private static final int OUTPUT_SLOT_SEARCH_ORDER_SLOT = 22;
    private static final int CARGO_FILTER_SLOT = 13;

    public LineTransferMenu(@Nonnull AbstractMachine machine) {
        super(machine);
    }

    @Override
    protected int[] getBorder() {
        return BORDER;
    }

    @Override
    protected int[] getInputBorder() {
        return INPUT_BORDER;
    }

    @Override
    protected int[] getOutputBorder() {
        return OUTPUT_BORDER;
    }

    @Override
    public int[] getInputSlot() {
        return INPUT_SLOT;
    }

    @Override
    public int[] getOutputSlot() {
        return OUTPUT_SLOT;
    }

    @Override
    public void init() {
        super.init();
        setSize(54);

        for (int slot : SPECIAL_BORDER) {
            this.addCustomItem(slot, Icon.SPECIAL_BORDER_ICON);
            this.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
        }

        this.addCustomItem(BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.LINE_HELPER.defaultIcon());
        this.addCustomItem(BLOCK_SEARCH_ORDER_SLOT, BlockSearchOrder.HELPER.defaultIcon());
        this.addCustomItem(CARGO_ORDER_SLOT, CargoOrder.HELPER.defaultIcon());
        this.addCustomItem(BLOCK_SEARCH_CYCLE_SLOT, BlockSearchCycle.HELPER.defaultIcon());
        this.addCustomItem(BLOCK_SEARCH_SELF_SLOT, BlockSearchSelf.HELPER.defaultIcon());

        this.addCustomItem(CARGO_MODE_SLOT, CargoMode.HELPER.defaultIcon());
        this.addCustomItem(CARGO_FILTER_SLOT, CargoFilter.HELPER.defaultIcon());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        Inventory inventory = blockMenu.toInventory();
        Location location = block.getLocation();

        blockMenu.addMenuClickHandler(BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.LINE_HELPER.getHandler(inventory, location, this.getSlimefunItem(), BLOCK_SEARCH_MODE_SLOT));
        blockMenu.addMenuClickHandler(BLOCK_SEARCH_ORDER_SLOT, BlockSearchOrder.HELPER.getHandler(inventory, location, this.getSlimefunItem(), BLOCK_SEARCH_ORDER_SLOT));
        blockMenu.addMenuClickHandler(CARGO_ORDER_SLOT, CargoOrder.HELPER.getHandler(inventory, location, this.getSlimefunItem(), CARGO_ORDER_SLOT));
        blockMenu.addMenuClickHandler(BLOCK_SEARCH_CYCLE_SLOT, BlockSearchCycle.HELPER.getHandler(inventory, location, this.getSlimefunItem(), BLOCK_SEARCH_CYCLE_SLOT));
        blockMenu.addMenuClickHandler(BLOCK_SEARCH_SELF_SLOT, BlockSearchSelf.HELPER.getHandler(inventory, location, this.getSlimefunItem(), BLOCK_SEARCH_SELF_SLOT));

        blockMenu.addMenuClickHandler(CARGO_MODE_SLOT, CargoMode.HELPER.getHandler(inventory, location, this.getSlimefunItem(), CARGO_MODE_SLOT));
        blockMenu.addMenuClickHandler(CARGO_FILTER_SLOT, CargoFilter.HELPER.getHandler(inventory, location, this.getSlimefunItem(), CARGO_FILTER_SLOT));
    }

    @Override
    public void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {
        BlockSearchMode.LINE_HELPER.checkAndUpdateIcon(inventory, location, BLOCK_SEARCH_MODE_SLOT);
        BlockSearchOrder.HELPER.checkAndUpdateIcon(inventory, location, BLOCK_SEARCH_ORDER_SLOT);
        CargoOrder.HELPER.checkAndUpdateIcon(inventory, location, CARGO_ORDER_SLOT);
        BlockSearchCycle.HELPER.checkAndUpdateIcon(inventory, location, BLOCK_SEARCH_CYCLE_SLOT);
        BlockSearchSelf.HELPER.checkAndUpdateIcon(inventory, location, BLOCK_SEARCH_SELF_SLOT);

        CargoMode.HELPER.checkAndUpdateIcon(inventory, location, CARGO_MODE_SLOT);
        CargoFilter.HELPER.checkAndUpdateIcon(inventory, location, CARGO_FILTER_SLOT);
    }
}
