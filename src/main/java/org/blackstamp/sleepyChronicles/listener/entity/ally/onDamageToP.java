package org.blackstamp.sleepyChronicles.listener.entity.ally;

import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.allyMob;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@Registrable
public class onDamageToP implements Listener {

    @EventHandler
    private void onDamageToE(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        Entity directEntity = e.getDamageSource().getDirectEntity();

        if(!(entity instanceof Player)) return;
        if (!(directEntity instanceof CraftEntity craftEntity)) return;
        net.minecraft.world.entity.Entity nmsEntity = craftEntity.getHandle();
        if(!(nmsEntity instanceof allyMob)) return;

        e.setCancelled(true);
        }
    }

