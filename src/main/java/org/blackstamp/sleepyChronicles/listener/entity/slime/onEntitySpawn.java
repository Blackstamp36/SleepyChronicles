package org.blackstamp.sleepyChronicles.listener.entity.slime;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.slime.seedGhostSlime;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Random;

@Registrable
public class onEntitySpawn implements Listener {

    @EventHandler
    private void onEntitySpawn(CreatureSpawnEvent e) {
        Random r = new Random();
        globalClass global = new globalClass();
        LivingEntity entity = e.getEntity();
        CraftLivingEntity craftEntity = (CraftLivingEntity) entity;

        if(entity.getScoreboardTags().contains("seedGhostSlime") &&  entity.getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.SLIME_SPLIT)){
            e.setCancelled(true);
        }

        if (entity.getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)
                || entity.getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
            if (global.getServerDay() >= 6 && entity instanceof Slime) {
                if(r.nextInt(1,1001) <= 100){
                    seedGhostSlime.spawnEntity(entity.getLocation(), 1);
                    e.setCancelled(true);
                }
            }
        }
    }
}

