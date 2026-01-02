package org.blackstamp.sleepychronicles.deprecated.listener.entity.llama;

import net.minecraft.world.entity.Entity;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.llama.aggresiveLlama;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@Registrable
public class onEntityDeath implements Listener {

    @EventHandler
    private void onEDeath(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();

        if(!(entity instanceof CraftEntity craftEntity)) return;
        Entity nmsEntity = craftEntity.getHandle();

        if(!(nmsEntity instanceof aggresiveLlama)) return;
        e.getDrops().clear();

    }
}

