package org.blackstamp.sleepychronicles.deprecated.listener.player;

import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.Player;

import java.util.UUID;

import static org.blackstamp.sleepychronicles.SleepyChronicles.chatPrefix;

@Registrable
public class onInventoryClose implements Listener {
    GlobalClass global = new GlobalClass();

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Inventory closedInventory = e.getInventory();
        Player p = (Player) e.getPlayer();

        if (closedInventory.getType() == InventoryType.CHEST && e.getView().getOriginalTitle().equals("§dTRINKETS")) {
            UUID uuid = p.getUniqueId();
            global.updateTrinkets(uuid, closedInventory);

            p.sendMessage(chatPrefix + "§aTrinkets saved!");
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 2F);
            p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.75F, 1.25F);
        }
    }
}
