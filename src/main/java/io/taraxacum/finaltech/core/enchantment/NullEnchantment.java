package io.taraxacum.finaltech.core.enchantment;

import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class NullEnchantment {

    public static void addAndHidden(@Nonnull ItemStack item) {
        item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);
        ItemStackUtil.addItemFlag(item, ItemFlag.HIDE_ENCHANTS);
    }

    public static boolean isPure(@Nonnull ItemStack item) {
        Map<Enchantment, Integer> enchantments = item.getItemMeta().getEnchants();
        if (enchantments.isEmpty()) {
            return false;
        }
        if (enchantments.size() == 1 && enchantments.containsKey(Enchantment.KNOCKBACK) && enchantments.get(Enchantment.KNOCKBACK) == 5) {
            return true;
        }
        return false;
    }
}
