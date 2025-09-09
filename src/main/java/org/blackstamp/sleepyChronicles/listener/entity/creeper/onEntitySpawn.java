package org.blackstamp.sleepyChronicles.listener.entity.creeper;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.creeper.missingId;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.creeper.suppressedCreeper;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.concurrent.ThreadLocalRandom;

@Registrable
public class onEntitySpawn implements Listener {

    @EventHandler
    private void onEntitySpawn(CreatureSpawnEvent e) {
        globalClass global = new globalClass();
        LivingEntity entity = e.getEntity();
        Location l = entity.getLocation();
        CraftLivingEntity craftEntity = (CraftLivingEntity) entity;
        net.minecraft.world.entity.LivingEntity nmsEntity = craftEntity.getHandle();

        boolean spawnRequirements = entity.getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)
                || entity.getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG);

        if(nmsEntity == null) return;
        if(!spawnRequirements) return;
        if(!(entity instanceof Creeper c)) return;
        if(!(global.getServerDay() >= 6)) return;
        int chance = ThreadLocalRandom.current().nextInt(0, 1000); // 0-999 (1000 chances)

        if(chance <= 99) summonSuppressed(l, e); // 10% spawn.

        else if(chance <= 249) summonMissingId(l, e); // 25% spawn.

        else c.setPowered(true); // 75% spawn.

        // 5/100 KITSUNEFOX (PROBABLY GONNA REMOVE IT)
    }

    private void summonSuppressed(Location l, EntitySpawnEvent e){
        Vec3 vec3 = new Vec3(l.getX(), l.getY(), l.getZ());
        Level nmsLevel = ((CraftWorld) l.getWorld()).getHandle();
        suppressedCreeper creeper = new suppressedCreeper(net.minecraft.world.entity.EntityType.CREEPER, nmsLevel);
        nmsLevel.addFreshEntity(creeper);
        creeper.setPos(vec3);
        e.setCancelled(true);

    }

    private void summonMissingId(Location l, EntitySpawnEvent e){
        Vec3 vec3 = new Vec3(l.getX(), l.getY(), l.getZ());
        Level nmsLevel = ((CraftWorld) l.getWorld()).getHandle();
        missingId creeper = new missingId(net.minecraft.world.entity.EntityType.CREEPER, nmsLevel);
        nmsLevel.addFreshEntity(creeper);
        creeper.setPos(vec3);
        e.setCancelled(true);

    }
}
