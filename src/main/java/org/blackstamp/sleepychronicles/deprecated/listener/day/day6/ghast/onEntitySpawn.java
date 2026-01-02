package org.blackstamp.sleepychronicles.deprecated.listener.day.day6.ghast;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.ghast.eyelessGhast;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.concurrent.ThreadLocalRandom;

@Registrable
public class onEntitySpawn implements Listener {

    @EventHandler
    private void onEntitySpawn(CreatureSpawnEvent e) {
        GlobalClass global = new GlobalClass();
        LivingEntity entity = e.getEntity();
        Location l = entity.getLocation();
        CraftLivingEntity craftEntity = (CraftLivingEntity) entity;
        net.minecraft.world.entity.LivingEntity nmsEntity = craftEntity.getHandle();

        boolean spawnRequirements = entity.getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)
                || entity.getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG);

        if(nmsEntity == null) return;
        if(!spawnRequirements) return;
        if(!(global.getServerDay() >= 6)) return;
        if(!(entity instanceof Ghast)) return;
        if(!(ThreadLocalRandom.current().nextInt(0, 1000) <= 299)) return;

        Vec3 vec3 = new Vec3(l.getX(), l.getY(), l.getZ());
        Level nmsLevel = ((CraftWorld) entity.getWorld()).getHandle();
        eyelessGhast ghast = new eyelessGhast(EntityType.GHAST, nmsLevel);
        nmsLevel.addFreshEntity(ghast);
        ghast.setPos(vec3);
        e.setCancelled(true);
    }
}
