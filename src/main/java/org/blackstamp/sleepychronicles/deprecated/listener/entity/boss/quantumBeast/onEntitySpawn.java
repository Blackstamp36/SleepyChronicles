package org.blackstamp.sleepychronicles.deprecated.listener.entity.boss.quantumBeast;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumBeast;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumCore;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

@Registrable
public class onEntitySpawn implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e){

        if(!(e.getEntity() instanceof CraftEntity craftEntity)) return;
        if(!(craftEntity.getHandle() instanceof quantumBeast entity)) return;

        spawnQuantumCore(entity, entity.getBukkitLivingEntity().getLocation());

    }

    private void spawnQuantumCore(quantumBeast entity, Location loc){
                ServerLevel nmsLvl = ((CraftWorld) loc.getWorld()).getHandle();
                quantumCore c = new quantumCore(EntityType.CREEPER, nmsLvl);
                c.setPos(loc.getX(), loc.getY(), loc.getZ());
                c.setOwner(entity);
                entity.setCore(c);
                nmsLvl.addFreshEntity(c);
    }
}
