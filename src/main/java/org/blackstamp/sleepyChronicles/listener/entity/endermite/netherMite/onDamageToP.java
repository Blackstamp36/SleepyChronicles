package org.blackstamp.sleepyChronicles.listener.entity.endermite.netherMite;

import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Random;

@Registrable
public class onDamageToP implements Listener {

    @EventHandler
    private void onDamageToE(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        Entity damager = e.getDamageSource().getCausingEntity();
        Random r = new Random();

        if (entity instanceof Player p && damager instanceof Endermite && entity.getScoreboardTags().contains("netherMite")) {
            int randomPower = r.nextInt(1,4);
            p.getLocation().createExplosion(randomPower);
        }
    }
}
