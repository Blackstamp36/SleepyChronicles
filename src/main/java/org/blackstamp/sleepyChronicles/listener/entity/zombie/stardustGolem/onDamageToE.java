package org.blackstamp.sleepyChronicles.listener.entity.zombie.stardustGolem;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@Registrable
public class onDamageToE implements Listener {

    @EventHandler
    private void onDamageToE(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        Entity damager = e.getDamageSource().getDirectEntity();

        if (damager instanceof Player p) {
            if (entity instanceof Zombie && entity.getScoreboardTags().contains("allyMob")) {
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F, 0.5F);
                e.setCancelled(true);

            }
        } else if(entity.getScoreboardTags().contains("stardustGolem") &&
        !e.getDamageSource().getDamageType().equals(DamageType.GENERIC_KILL)){
            e.setCancelled(true);
        }
    }
}
