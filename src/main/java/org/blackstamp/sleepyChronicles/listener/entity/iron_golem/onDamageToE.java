package org.blackstamp.sleepyChronicles.listener.entity.iron_golem;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@Registrable
public class onDamageToE implements Listener {

    @EventHandler
    private void onDamageToE(EntityDamageEvent e) {
        globalClass global = new globalClass();
        Entity entity = e.getEntity();
        DamageType damageType = e.getDamageSource().getDamageType();

        if (entity instanceof IronGolem && entity.getScoreboardTags().contains("quantumGolem")) {
            if (damageType.equals(DamageType.ARROW)) {
                for(Player n : entity.getLocation().getNearbyPlayers(5)){
                    global.spawnParticles(n.getLocation(), Particle.HAPPY_VILLAGER, null,  25);
                    n.playSound(n.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1, 0.5F);
                }

                e.setCancelled(true);
            }
        }
    }
}
