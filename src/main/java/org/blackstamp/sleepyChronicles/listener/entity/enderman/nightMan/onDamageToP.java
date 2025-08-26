package org.blackstamp.sleepyChronicles.listener.entity.enderman.nightMan;

import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Sound;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
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

        if(entity instanceof Player p && damager instanceof Enderman && damager.getScoreboardTags().contains("nightMan")) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 2, false, false));
            p.playSound(p.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_HURT, 0.25F, 0.5F);

        }
    }
}
