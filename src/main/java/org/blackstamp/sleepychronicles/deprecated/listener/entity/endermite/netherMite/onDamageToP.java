package org.blackstamp.sleepychronicles.deprecated.listener.entity.endermite.netherMite;

import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.endermite.netherMite;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.concurrent.ThreadLocalRandom;

@Registrable
public class onDamageToP implements Listener {

    @EventHandler
    private void onDamageToE(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        Entity damager = e.getDamageSource().getCausingEntity();

        if(!(entity instanceof Player p)) return;
        if(!(damager instanceof CraftEntity craftDamager)) return;
        net.minecraft.world.entity.Entity nmsDamager = craftDamager.getHandle();

        if(nmsDamager == null) return;
        if(!(nmsDamager instanceof netherMite)) return;

        int randomPower = ThreadLocalRandom.current().nextInt(1,3);
        p.getLocation().createExplosion(randomPower);
    }
}
