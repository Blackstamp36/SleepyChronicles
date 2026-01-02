package org.blackstamp.sleepychronicles.deprecated.listener.day.day1.prepare_item;

import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

@Registrable
public class onReforge implements Listener {
    GlobalClass global = new GlobalClass();

    @EventHandler
    private void onReforge(PrepareAnvilEvent e){
    ItemStack firstItem = e.getInventory().getFirstItem();
    ItemStack secondItem = e.getInventory().getSecondItem();
    if(firstItem == null) return;
    if(secondItem == null) return;
    if(!secondItem.hasItemMeta()) return;
    if(!(secondItem.getItemMeta() instanceof EnchantmentStorageMeta meta)) return;

    if(global.isCustomItem(firstItem, "vortex_shortbow") && meta.hasStoredEnchant(Enchantment.INFINITY))
            e.setResult(null);

    }
}