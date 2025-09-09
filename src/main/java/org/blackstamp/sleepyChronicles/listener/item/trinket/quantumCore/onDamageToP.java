package org.blackstamp.sleepyChronicles.listener.item.trinket.quantumCore;

import io.papermc.paper.event.entity.EntityKnockbackEvent;
import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.item.trinket.trinketItems;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.blackstamp.sleepyChronicles.util.data.playerData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

@Registrable
public class onDamageToP implements Listener {
    trinketItems trinkets = new trinketItems();

    @EventHandler
    private void onKnocbackToP(EntityKnockbackEvent e){
        globalClass global = new globalClass();
        Entity entity = e.getEntity();

        if(entity instanceof Player p){
            playerData data = global.getPlayerData(p.getUniqueId());
            Inventory perksInv = data.getTrinketsAsInventory(p);

            if(perksInv.contains(trinkets.createQuantumCore()) || perksInv.contains(trinkets.createQuantumReactor())){
                e.setCancelled(true);
            }
        }

    }
}
