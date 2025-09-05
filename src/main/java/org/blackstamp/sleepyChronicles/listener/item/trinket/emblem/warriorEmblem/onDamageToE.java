package org.blackstamp.sleepyChronicles.listener.item.trinket.emblem.warriorEmblem;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.item.trinket.trinketItems;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.blackstamp.sleepyChronicles.util.data.playerData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.Inventory;

@Registrable
public class onDamageToE implements Listener {
    trinketItems trinkets = new trinketItems();

    @EventHandler
    private void onDamageToE(EntityDamageEvent e){
        globalClass global = new globalClass();
        Entity damager = e.getDamageSource().getCausingEntity();

        if(damager instanceof Player p){
            playerData data = global.getPlayerData(p.getUniqueId());
            Inventory perksInv = data.getTrinketsAsInventory(p);
            double originalDamage = e.getDamage();
            double modifiedDamage;

            if(perksInv.contains(trinkets.createWarriorEmblem())){
                modifiedDamage = originalDamage + (originalDamage * 0.15);
                e.setDamage(modifiedDamage);

            }
        }
    }
}

