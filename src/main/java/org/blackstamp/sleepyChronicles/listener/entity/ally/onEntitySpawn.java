package org.blackstamp.sleepyChronicles.listener.entity.ally;

import net.minecraft.server.level.ServerLevel;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.allyMob;
import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

@Registrable
public class onEntitySpawn implements Listener {

    @EventHandler
    private void onEntitySpawn(EntitySpawnEvent e) {
        Entity entity = e.getEntity();

        if (entity instanceof CraftEntity craftEntity) {
            net.minecraft.world.entity.Entity nmsEntity = craftEntity.getHandle();

            if (nmsEntity instanceof allyMob ally) {
                Player summoner = Bukkit.getPlayer(ally.getSummonerUUID());
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        double distanceToEntity = summoner.getLocation().distance(entity.getLocation());

                        if(entity.isDead()
                                || summoner.isDead()
                                || (!summoner.hasLineOfSight(entity) && isInFieldOfView(summoner, entity))
                                || !isInFieldOfView(summoner, entity)
                                || distanceToEntity >= 20.0){
                            nmsEntity.kill((ServerLevel) nmsEntity.level());
                            this.cancel();

                        }
                    }
                }.runTaskTimer(sleepyChronicles.getter(), 40, 40);
            }
        }
    }

    private boolean isInFieldOfView(Player player, Entity entity) {
        Location playerLoc = player.getEyeLocation();
        Vector playerDir = playerLoc.getDirection().normalize();

        Vector toEntity = entity.getLocation().toVector().subtract(playerLoc.toVector()).normalize();

        double dot = playerDir.dot(toEntity);

        double requiredAngle = 0.55;

        return dot > requiredAngle;
    }

}
