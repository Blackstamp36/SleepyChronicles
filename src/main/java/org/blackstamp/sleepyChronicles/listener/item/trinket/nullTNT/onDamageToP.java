package org.blackstamp.sleepyChronicles.listener.item.trinket.nullTNT;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.item.trinket.trinketItems;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.blackstamp.sleepyChronicles.util.data.playerData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.Inventory;

import java.util.Random;

@Registrable
public class onDamageToP {
    trinketItems trinkets = new trinketItems();

    @EventHandler
    private void onDamageToP(EntityDamageEvent e){
        Random r = new Random();
        globalClass global = new globalClass();
        Entity entity = e.getEntity();
        EntityDamageEvent.DamageCause damageCause = e.getCause();

        if(entity instanceof Player p){
            playerData data = global.getPlayerData(p.getUniqueId());
            Inventory perksInv = data.getTrinketsAsInventory(p);
            double originalDamage = e.getDamage();
            double modifiedDamage;

            boolean b = damageCause.equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)
                    || damageCause.equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION);

            if(perksInv.contains(trinkets.createNullTNT())){
                if(b){
                    modifiedDamage = originalDamage - (originalDamage * 0.25);
                    e.setDamage(modifiedDamage);

                }
            } else if(perksInv.contains(trinkets.createFoundTNT())){
                if(b){
                    modifiedDamage = originalDamage - (originalDamage * 0.50);
                    e.setDamage(modifiedDamage);

                }

            }

        }

    }
}
