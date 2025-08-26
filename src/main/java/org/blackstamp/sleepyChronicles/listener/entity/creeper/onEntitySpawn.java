package org.blackstamp.sleepyChronicles.listener.entity.creeper;

import net.minecraft.world.entity.Mob;
import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.creeper.missingId;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.creeper.suppressedCreeper;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.fox.kitsuneFox;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.Creeper;
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
        Location l = entity.getLocation();
        CraftLivingEntity craftEntity = (CraftLivingEntity) entity;
        net.minecraft.world.entity.LivingEntity nmsEntity = craftEntity.getHandle();

        if (!(nmsEntity instanceof Mob mob)) return;

        if (entity.getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)
                || entity.getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
            if (global.getServerDay() >= 6 && entity instanceof Creeper) {
                if (r.nextInt(101) <= 5 && l.getWorld().getBiome(l).toString().contains("PALE")) {
                    kitsuneFox.spawnEntity(entity.getLocation(), 1);
                    e.setCancelled(true);

                } else if (r.nextInt(101) <= 25) {
                    suppressedCreeper.spawnEntity(entity.getLocation(), 1);
                    e.setCancelled(true);

                } else if (r.nextInt(101) <= 50) {
                    missingId.spawnEntity(entity.getLocation(), 1);
                    e.setCancelled(true);

                } else {
                    ((net.minecraft.world.entity.monster.Creeper) mob).setPowered(true);
                }
            }
        }
    }
}
