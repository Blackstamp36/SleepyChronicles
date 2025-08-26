package org.blackstamp.sleepyChronicles.listener.entity.ghast;

import net.minecraft.world.entity.Mob;
import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.ghast.eyelessGhast;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Random;

@Registrable
public class onEntitySpawn implements Listener {

    @EventHandler
    private void onEntitySpawn(CreatureSpawnEvent e) {
        globalClass global = new globalClass();
        Random r = new Random();
        LivingEntity entity = e.getEntity();
        CraftLivingEntity craftEntity = (CraftLivingEntity) entity;
        net.minecraft.world.entity.LivingEntity nmsEntity = craftEntity.getHandle();

        if (!(nmsEntity instanceof Mob mob)) return;

        if(entity.getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)
                || entity.getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)){

            if (global.getServerDay() >= 6 && entity instanceof Ghast && r.nextInt(101) <= 25) {
                eyelessGhast.spawnEntity(entity.getLocation(), 1);
                e.setCancelled(true);

            }
        }
    }
}
