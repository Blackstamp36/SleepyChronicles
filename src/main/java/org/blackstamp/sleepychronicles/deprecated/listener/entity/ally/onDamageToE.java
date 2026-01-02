package org.blackstamp.sleepychronicles.deprecated.listener.entity.ally;

import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.summonableMob;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@Registrable
public class onDamageToE implements Listener {

    @EventHandler
    private void onDamageToE(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        Entity damager = e.getDamageSource().getDirectEntity();

        if (!(damager instanceof Player p)) return;
        if (!(entity instanceof CraftEntity craftEntity)) return;

        net.minecraft.world.entity.Entity nmsEntity = craftEntity.getHandle();

        if(!(nmsEntity instanceof summonableMob)) return;

        e.setCancelled(true);
        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT,0.85F,0.25F);

        }
    }

