package org.blackstamp.sleepychronicles.deprecated.listener.item.trinket.nullTNT;

import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.deprecated.items.trinket.trinketItems;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.blackstamp.sleepychronicles.global.utils.data.PlayerData;
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
        GlobalClass global = new GlobalClass();
        Entity entity = e.getEntity();
        EntityDamageEvent.DamageCause damageCause = e.getCause();

        if(entity instanceof Player p){
            PlayerData data = global.getPlayerData(p.getUniqueId());
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
