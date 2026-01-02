package org.blackstamp.sleepychronicles.deprecated.listener.item.trinket.emblem.warriorEmblem;

import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.deprecated.items.trinket.trinketItems;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.blackstamp.sleepychronicles.global.utils.data.PlayerData;
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
        GlobalClass global = new GlobalClass();
        Entity damager = e.getDamageSource().getCausingEntity();

        if(damager instanceof Player p){
            PlayerData data = global.getPlayerData(p.getUniqueId());
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

