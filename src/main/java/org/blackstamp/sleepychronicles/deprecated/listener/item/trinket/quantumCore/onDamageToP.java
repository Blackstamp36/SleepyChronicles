package org.blackstamp.sleepychronicles.deprecated.listener.item.trinket.quantumCore;

import io.papermc.paper.event.entity.EntityKnockbackEvent;
import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.deprecated.items.trinket.trinketItems;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.blackstamp.sleepychronicles.global.utils.data.PlayerData;
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
        GlobalClass global = new GlobalClass();
        Entity entity = e.getEntity();

        if(entity instanceof Player p){
            PlayerData data = global.getPlayerData(p.getUniqueId());
            Inventory perksInv = data.getTrinketsAsInventory(p);

            if(perksInv.contains(trinkets.createQuantumCore()) || perksInv.contains(trinkets.createQuantumReactor())){
                e.setCancelled(true);
            }
        }

    }
}
