package org.blackstamp.sleepyChronicles.listener.entity.creaking;

import net.minecraft.world.entity.Mob;
import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.creaking.bobCreaking;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.Creaking;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
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

        if (!entity.getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)) return;
        if (!entity.getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) return;
        if (!(global.getServerDay() >= 6)) return; // ONLY IN DAY 6 AND FORWARD

        if (entity instanceof Zombie && r.nextInt(101) <= 9) {
            e.setCancelled(true);
            bobCreaking.spawnEntity(entity.getLocation(), 1);
        } else if (entity instanceof Creaking) {
            e.setCancelled(true);
            bobCreaking.spawnEntity(entity.getLocation(), 1);
        }

    }
}

