package io.taraxacum.finaltech.core.item.machine.unit;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.OneLineStorageUnitMenu;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.dto.ItemAmountWrapper;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;

import javax.annotation.Nonnull;
 
public class DistributeRightStorageUnit extends AbstractStorageUnit implements RecipeItem {
    public DistributeRightStorageUnit(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new OneLineStorageUnitMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = StorageCacheUtils.getMenu(block.getLocation());
        Inventory inventory = blockMenu.toInventory();
        int beginSlot = 0;
        int endSlot = 0;
        int i;
        ItemAmountWrapper itemAmountWrapper = null;
        for (i = 0; i < this.getInputSlot().length; i++) {
            if (!ItemStackUtil.isItemNull(blockMenu.getItemInSlot(i))) {
                itemAmountWrapper = new ItemAmountWrapper(blockMenu.getItemInSlot(i));
                beginSlot = i;
                endSlot = i++;
                break;
            }
        }
        for (; i < this.getInputSlot().length; i++) {
            if (ItemStackUtil.isItemNull(blockMenu.getItemInSlot(i))) {
                endSlot = i;
            } else if (ItemStackUtil.isItemSimilar(itemAmountWrapper, blockMenu.getItemInSlot(i))) {
                itemAmountWrapper.addAmount(blockMenu.getItemInSlot(i).getAmount());
                endSlot = i;
            } else {
                int amount = itemAmountWrapper.getAmount() / (endSlot + 1 - beginSlot);
                if (amount > 0) {
                    ItemStack itemStack = ItemStackUtil.cloneItem(itemAmountWrapper.getItemStack());
                    for (int j = beginSlot; j <= endSlot; j++) {
                        itemStack.setAmount(amount);
                        inventory.setItem(j, itemStack);
                    }
                    itemStack.setAmount(amount + (itemAmountWrapper.getAmount() % (endSlot + 1 - beginSlot)));
                    inventory.setItem(beginSlot, itemStack);
                }
                itemAmountWrapper = new ItemAmountWrapper(blockMenu.getItemInSlot(i));
                beginSlot = i;
                endSlot = i;
            }
        }
        if (beginSlot != endSlot) {
            int amount = itemAmountWrapper.getAmount() / (endSlot + 1 - beginSlot);
            if (amount > 0) {
                ItemStack itemStack = ItemStackUtil.cloneItem(itemAmountWrapper.getItemStack());
                for (int j = beginSlot; j <= endSlot; j++) {
                    itemStack.setAmount(amount);
                    inventory.setItem(j, itemStack);
                }
                itemStack.setAmount(amount + itemAmountWrapper.getAmount() % (endSlot + 1 - beginSlot));
                inventory.setItem(beginSlot, itemStack);
            }
        }
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this,
                String.valueOf(MachineUtil.calMachineSlotSize(this)));
    }
}
