package org.blackstamp.sleepyChronicles.listener.player;

import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

@Registrable
public class onDamageToE implements Listener {

    @EventHandler
    private void onPlayerDamage(EntityDamageEvent e) {

        if (e.getDamageSource().getCausingEntity() instanceof Player p) {
            if(p.hasPotionEffect(PotionEffectType.WEAVING)){
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT,0.75F,0.25F);
                e.setCancelled(true);
            }
        }

    }
}

