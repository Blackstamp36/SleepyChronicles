package org.blackstamp.sleepyChronicles.listener.player;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.item.itemRegister;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.blackstamp.sleepyChronicles.util.data.playerData;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static org.blackstamp.sleepyChronicles.sleepyChronicles.chatPrefix;

@Registrable
public class onInventoryClick implements Listener {
    itemRegister iR = new itemRegister();
    playerData data = new playerData();
    globalClass global = new globalClass();

    @EventHandler
    private void onInventoryClick(InventoryClickEvent e) {
        Inventory currentInv = e.getInventory();
        Inventory clickedInv = e.getClickedInventory();
        ItemStack clickedItem = e.getCurrentItem();

        if (data.isDecoration(clickedItem)) {
            e.setCancelled(true);
            return;
        }

        if (clickedItem != null) {
            if (clickedItem.hasItemMeta() && e.getView().getOriginalTitle().equals("§eITEMS")) {
                Player p = (Player) e.getWhoClicked();
                Inventory pInv = p.getInventory();
                int page = iR.getPageNumber(currentInv);

                if(global.isBackItem(clickedItem)){
                    if(page != 1) {
                        page -= 1;
                        p.openInventory(iR.getInventoryForPage(page));
                        p.playSound(p.getLocation(), Sound.BLOCK_CHEST_CLOSE, 0.75F, 1.25F);

                    }
                    e.setCancelled(true);

                } else if(global.isNextItem(clickedItem)){
                    if(page != 2) {
                        page += 1;
                        p.openInventory(iR.getInventoryForPage(page));
                        p.playSound(p.getLocation(), Sound.BLOCK_CHEST_CLOSE, 0.75F, 1.25F);
                    }
                    e.setCancelled(true);

                } else if(!clickedInv.equals(pInv)){
                    e.setCancelled(true);
                    pInv.addItem(clickedItem);
                    p.sendMessage(chatPrefix + "§aReceiving item..");
                    p.playSound(p.getLocation(), Sound.BLOCK_SHULKER_BOX_OPEN, 0.5F, 1.5F);

                    }
                }
        }
    }
}
