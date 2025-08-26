package org.blackstamp.sleepyChronicles.listener.entity.zombie.paleSoul;

import com.destroystokyo.paper.ParticleBuilder;
import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Fox;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@Registrable
public class onEntityDeath implements Listener {
    globalClass global = new globalClass();

    @EventHandler
    private void onEDeath(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();

        if (entity instanceof Zombie && entity.getScoreboardTags().contains("paleSoul")) {
            e.getDrops().clear();
            e.setDroppedExp(0);
            for(Player nearby : entity.getLocation().getNearbyPlayers(15)){
                Location l = entity.getLocation();
                ParticleBuilder pBuilder = new ParticleBuilder(Particle.SOUL);
                pBuilder.location(l)
                        .count(100)
                        .offset(0.25, 0.25, 0.25)
                        .location(l.getWorld(), l.getX(), l.getY() + 1, l.getZ())
                        .spawn();

                nearby.sendActionBar(ChatColor.of("#cfc4c3") + "A nearby pale soul has fallen..");
                nearby.playSound(nearby.getLocation(), Sound.ENTITY_ALLAY_DEATH, 1,0.5F);

            }
        }
    }
}
