package org.blackstamp.sleepyChronicles.listener.entity.spider;

import net.minecraft.world.entity.Mob;
import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.spider.voidbornSpider;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
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
        CraftLivingEntity craftEntity = (CraftLivingEntity) entity;
        net.minecraft.world.entity.LivingEntity nmsEntity = craftEntity.getHandle();

        if (!(nmsEntity instanceof Mob mob)) return;

        if (entity.getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)
                || entity.getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
            if (global.getServerDay() >= 6 && entity instanceof Spider) {
                voidbornSpider.spawnEntity(entity.getLocation(), 1);
                e.setCancelled(true);

            }
        }
    }
}
