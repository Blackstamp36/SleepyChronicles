package org.blackstamp.sleepyChronicles.listener.entity.zombie.paleSoul;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Creaking;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;

@Registrable
public class onDamageToE implements Listener {

    @EventHandler
    private void onDamageToE(EntityDamageEvent e) {
        globalClass global = new globalClass();
        Entity entity = e.getEntity();
        Entity damager = e.getDamageSource().getDirectEntity();

        if (damager instanceof Player p) {
            if (entity instanceof Zombie && entity.getScoreboardTags().contains("paleSoul")) {
                global.spawnParticles(entity.getLocation(), Particle.BLOCK, Material.PALE_OAK_LOG, 10);
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F, 0.5F);
                e.setCancelled(true);

            }
        }
    }
}
