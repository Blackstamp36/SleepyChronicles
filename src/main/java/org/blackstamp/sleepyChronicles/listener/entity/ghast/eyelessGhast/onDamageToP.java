package org.blackstamp.sleepyChronicles.listener.entity.ghast.eyelessGhast;

import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Registrable
public class onDamageToP implements Listener {

    @EventHandler
    private void onDamageToE(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        Entity damager = e.getDamageSource().getCausingEntity();

        if(entity instanceof Player p && damager instanceof Ghast && damager.getScoreboardTags().contains("eyelessGhast")) {
            damager.teleport(p.getLocation());
            Bukkit.getScheduler().runTaskLater(sleepyChronicles.getter(), () -> {
                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 0, false, false));
                p.playSound(p.getLocation(), Sound.ENTITY_ALLAY_HURT,1,0.5F);
            }, 1);
        }
    }
}
