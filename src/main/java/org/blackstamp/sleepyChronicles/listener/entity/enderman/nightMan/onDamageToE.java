package org.blackstamp.sleepyChronicles.listener.entity.enderman.nightMan;

import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Sound;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Registrable
public class onDamageToE implements Listener {

    @EventHandler
    private void onDamageToP(EntityDamageEvent e) {
        Entity entity = e.getEntity();

        if(entity instanceof Enderman && entity.getScoreboardTags().contains("nightMan")) {
            if(e.getCause().equals(EntityDamageEvent.DamageCause.CUSTOM)){
                if(entity.isInRain()){
                    e.setCancelled(true);
                }
            } else if(e.getCause().equals(EntityDamageEvent.DamageCause.DROWNING)){
                e.setCancelled(true);
            }

        }
    }
}
