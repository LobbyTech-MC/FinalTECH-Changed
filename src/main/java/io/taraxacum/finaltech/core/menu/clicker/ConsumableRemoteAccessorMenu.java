package io.taraxacum.finaltech.core.menu.clicker;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.taraxacum.finaltech.core.interfaces.DigitalItem;
import io.taraxacum.finaltech.core.item.machine.clicker.AbstractClickerMachine;
import io.taraxacum.finaltech.util.LocationUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;

import javax.annotation.Nonnull;
 
public class ConsumableRemoteAccessorMenu extends AbstractClickerMenu {
    private static final int[] BORDER = new int[]{0, 1, 2, 3, 5, 6, 7, 8};
    private static final int[] INPUT_BORDER = new int[0];
    private static final int[] OUTPUT_BORDER = new int[0];
    private static final int[] INPUT_SLOT = new int[]{4};
    private static final int[] OUTPUT_SLOT = new int[]{4};

    private final int range;

    public ConsumableRemoteAccessorMenu(@Nonnull AbstractClickerMachine slimefunItem, int range) {
        super(slimefunItem);
        this.range = range;
    }

    @Override
    protected void doFunction(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull Player player) {
        // TODO async
        ItemStack item = blockMenu.getItemInSlot(INPUT_SLOT[0]);
        if (!ItemStackUtil.isItemNull(item)) {
            int amount = item.getAmount();
            SlimefunItem slimefunItem = SlimefunItem.getByItem(item);
            if (slimefunItem instanceof DigitalItem digitalItem) {
                blockMenu.close();

                int digit = digitalItem.getDigit();

                BlockData blockData = block.getState().getBlockData();
                if (blockData instanceof Directional) {
                    BlockFace blockFace = ((Directional) blockData).getFacing();
                    Block targetBlock = block;

                    if (digit > 0) {
                        for (int i = 0; i < digit; i++) {
                            targetBlock = targetBlock.getRelative(blockFace);
                        }

                        if (StorageCacheUtils.getMenu(targetBlock.getLocation()) != null) {
                            BlockMenu targetBlockMenu = StorageCacheUtils.getMenu(targetBlock.getLocation());
                            if (targetBlockMenu.canOpen(targetBlock, player)) {
                                JavaPlugin javaPlugin = this.getSlimefunItem().getAddon().getJavaPlugin();
                                Block finalTargetBlock = targetBlock;
                                javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, finalTargetBlock));
                                javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawLineByDistance(javaPlugin, Particle.WAX_OFF, 0, 0.25, LocationUtil.getCenterLocation(block), LocationUtil.getCenterLocation(finalTargetBlock)));
                                targetBlockMenu.open(player);
                            }
                        }
                    } else if (digit == 0) {
                        for (int i = 0; i < this.range; i++) {
                            targetBlock = targetBlock.getRelative(blockFace);
                            if (StorageCacheUtils.getMenu(targetBlock.getLocation()) != null) {
                                BlockMenu targetBlockMenu = StorageCacheUtils.getMenu(targetBlock.getLocation());
                                if (targetBlockMenu.canOpen(targetBlock, player)) {
                                    JavaPlugin javaPlugin = this.getSlimefunItem().getAddon().getJavaPlugin();
                                    Block finalTargetBlock = targetBlock;
                                    javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, finalTargetBlock));
                                    javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawLineByDistance(javaPlugin, Particle.WAX_OFF, 0, 0.25, LocationUtil.getCenterLocation(block), LocationUtil.getCenterLocation(finalTargetBlock)));
                                    targetBlockMenu.open(player);
                                    break;
                                }
                            }
                        }
                    }
                }

                if (item.getAmount() == amount) {
                    item.setAmount(item.getAmount() - 1);
                }
                return;
            }
        }

        JavaPlugin javaPlugin = this.getSlimefunItem().getAddon().getJavaPlugin();
        javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, block));
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
    protected void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {

    }
}
