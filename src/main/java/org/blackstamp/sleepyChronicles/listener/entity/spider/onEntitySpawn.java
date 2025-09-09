package org.blackstamp.sleepyChronicles.listener.entity.spider;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.spider.voidbornSpider;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.wither_boss.mechanicalEye;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

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
        if(!(entity instanceof Spider)) return;
        if(!(global.getServerDay() >= 6)) return;

        Vec3 vec3 = new Vec3(l.getX(), l.getY(), l.getZ());
        Level nmsLevel = ((CraftWorld) entity.getWorld()).getHandle();
        voidbornSpider spider = new voidbornSpider(EntityType.SPIDER, nmsLevel);
        nmsLevel.addFreshEntity(spider);
        spider.setPos(vec3);
        e.setCancelled(true);

    }
}
