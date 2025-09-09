package org.blackstamp.sleepyChronicles.listener.entity.creeper.suppressedCreeper;

import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
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
            if (damager instanceof Creeper && damager.getScoreboardTags().contains("suppressedCreeper")) {
                Bukkit.getScheduler().runTaskLater(sleepyChronicles.getter(), () -> {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0, true, false));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 200, 0, true, false));
                    p.playSound(p.getLocation(), Sound.ITEM_TRIDENT_THUNDER,1,0.5F);

                }, 1);
            }
        }
    }
}