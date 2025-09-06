package org.blackstamp.sleepyChronicles.listener.item.trinket.missingNo;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.item.trinket.trinketItems;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.blackstamp.sleepyChronicles.util.data.playerData;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.Inventory;

import java.util.Random;

import static org.blackstamp.sleepyChronicles.sleepyChronicles.chatPrefix;

@Registrable
public class onDamageToP implements Listener {
    trinketItems trinkets = new trinketItems();

    @EventHandler
    private void onDamageToP(EntityDamageEvent e){
        Random r = new Random();
        globalClass global = new globalClass();
        Entity entity = e.getEntity();

        if(entity instanceof Player p){
            playerData data = global.getPlayerData(p.getUniqueId());
            Inventory perksInv = data.getTrinketsAsInventory(p);

            if(perksInv.contains(trinkets.createMissingNo()) && r.nextInt(100) <= 2){
                e.setCancelled(true);
                perksInv.removeItem(trinkets.createMissingNo());
                global.updateTrinkets(p.getUniqueId(), perksInv);
                p.sendMessage(chatPrefix + "§cYour " + trinkets.createMissingNo().getItemMeta().getDisplayName() + " §cbroke!");
                p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1,0.75F);
            } else if(perksInv.contains(trinkets.createNullPointerException()) && r.nextInt(100) <= 4){
                p.sendMessage(chatPrefix + "§aYour " + trinkets.createNullPointerException().getItemMeta().getDisplayName() + " §asaved you!");
                e.setCancelled(true);
            }

        }

    }
}
