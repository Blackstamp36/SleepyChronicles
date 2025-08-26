package org.blackstamp.sleepyChronicles.listener.entity.creeper.missingId;

import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Registrable
public class onDamageToP implements Listener {

    @EventHandler
    private void onDamageToP(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        Entity damager = e.getDamageSource().getCausingEntity();

        if(entity instanceof Player p) {
            if (damager instanceof Creeper && damager.getScoreboardTags().contains("missingId")) {
                Bukkit.getScheduler().runTaskLater(sleepyChronicles.getter(), () ->
                        p.addPotionEffect(new PotionEffect(
                                PotionEffectType.UNLUCK,
                                600,
                                1,
                                true,
                                false)),
                        1);
            }
        }
    }
}