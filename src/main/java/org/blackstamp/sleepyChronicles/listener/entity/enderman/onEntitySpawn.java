package org.blackstamp.sleepyChronicles.listener.entity.enderman;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.enderman.nightMan;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.entity.Enderman;
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

        if (entity.getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)
                || entity.getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {

            if (global.getServerDay() >= 6 && entity instanceof Enderman) {
                if (r.nextInt(101) <= 20) {
                    nightMan.spawnEntity(entity.getLocation(), 1);
                    e.setCancelled(true);

                }
            }
        }
    }
}
