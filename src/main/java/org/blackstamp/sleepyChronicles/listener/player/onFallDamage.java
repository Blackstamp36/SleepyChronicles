package org.blackstamp.sleepyChronicles.listener.player;

import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.UUID;

import static org.blackstamp.sleepyChronicles.globalClass.cancelFallDamage;

@Registrable
public class onFallDamage implements Listener {

    @EventHandler
    private void onFallDamage(EntityDamageEvent e){

        if(e.getEntity() instanceof Player p){
            UUID uuid = p.getUniqueId();

            if(e.getDamageSource().getDamageType().equals(DamageType.FALL)){
                if(cancelFallDamage.get(uuid)){
                    e.setCancelled(true);
                    cancelFallDamage.put(uuid, false);
                }

            }
        }
    }

}
