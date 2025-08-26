package org.blackstamp.sleepyChronicles.listener.player;

import com.destroystokyo.paper.ParticleBuilder;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

import java.util.Random;

import static org.blackstamp.sleepyChronicles.sleepyChronicles.PREFIX;
import static org.blackstamp.sleepyChronicles.sleepyChronicles.serverDay;

@Registrable
public class onSleep implements Listener {

    @EventHandler
    private void onSleep(PlayerBedEnterEvent e){
        Player p = e.getPlayer();
        long time = p.getWorld().getTime();
        Location l = p.getLocation();
        ParticleBuilder pB = new ParticleBuilder(Particle.EXPLOSION_EMITTER);
        pB.count(1);
        pB.offset(0.25,0.25,0.25);
        pB.location(e.getBed().getLocation());

        Random r = new Random();

        if(serverDay >= 3) {
            if (time >= 12000 && time < 22000) {
                int chance = r.nextInt(101);

                if(serverDay >= 6) chance = 30;

                p.sendMessage(PREFIX + "§cAttempting to sleep.. §7(Chance: " + chance + "<=30)");
                p.playSound(p.getLocation(), Sound.ENTITY_ALLAY_ITEM_TAKEN, 1, 0.5F);
                if (chance <= 30) {
                    p.playSound(p.getLocation(), Sound.ENTITY_ALLAY_DEATH, 1, 0.5F);
                    l.createExplosion(4);
                    e.setCancelled(true);
                }
            } else {
                p.sendMessage(PREFIX +  "§cYou can't sleep yet!");
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0.5F);
            }
        }
    }
}
