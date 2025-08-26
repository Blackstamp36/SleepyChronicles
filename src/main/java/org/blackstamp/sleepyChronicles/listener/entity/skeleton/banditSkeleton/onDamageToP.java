package org.blackstamp.sleepyChronicles.listener.entity.skeleton.banditSkeleton;

import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
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

        if (entity instanceof Player p && damager instanceof Skeleton && damager.getScoreboardTags().contains("banditSkeleton")) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 80, 0, false, false));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 1, false, false));
            p.playSound(p.getLocation(), Sound.ENTITY_ALLAY_ITEM_TAKEN, 1, 0.5F);

        }
    }
}
