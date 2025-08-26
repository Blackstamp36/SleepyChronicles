package org.blackstamp.sleepyChronicles.listener.player;

import org.blackstamp.sleepyChronicles.util.Registrable;
import org.blackstamp.sleepyChronicles.util.data.playerData;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Registrable
public class onInventoryClick implements Listener {
    playerData data = new playerData();

    @EventHandler
    private void onInventoryClick(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();

        if (data.isDecoration(item)) {
            e.setCancelled(true);
            return;
        }

        if (item != null) {
            if (item.hasItemMeta() && e.getView().getOriginalTitle().equals("§eITEMS")) {
                Player p = (Player) e.getWhoClicked();
                Inventory pInv = p.getInventory();
                e.setCancelled(true);
                pInv.addItem(item);
                p.playSound(p.getLocation(), Sound.BLOCK_SHULKER_BOX_OPEN, 0.5F, 1.5F);
            }
        }
    }
}
