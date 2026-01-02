package org.blackstamp.sleepychronicles.deprecated.listener.entity.boss.emperorOfDarkness;

import net.minecraft.world.entity.LivingEntity;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.ghast.emperorOfDarkness;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@Registrable
public class onEntityDeath implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {

        if(!(e.getEntity() instanceof CraftEntity craftEntity)) return;
        if(!(craftEntity.getHandle() instanceof LivingEntity entity)) return;

        if(!(entity instanceof emperorOfDarkness)) return;
        e.setDroppedExp(0);
        e.getDrops().clear();

        if(e.getEntity().getKiller() == null) return;
    }
}

