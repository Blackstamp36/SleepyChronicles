package org.blackstamp.sleepychronicles.deprecated.listener.entity.ally;

import net.minecraft.server.level.ServerLevel;
import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.summonableMob;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.zombie.stardustGolem;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
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
    GlobalClass global = new GlobalClass();

    @EventHandler
    private void onEntitySpawn(EntitySpawnEvent e) {
        Entity entity = e.getEntity();

        if (!(entity instanceof CraftEntity craftEntity)) return;
        net.minecraft.world.entity.Entity nmsEntity = craftEntity.getHandle();

        if(!(nmsEntity instanceof summonableMob ally)) return;
        if(nmsEntity instanceof stardustGolem) return;

        Player summoner = Bukkit.getPlayer(ally.getSummonerUUID());

        if(summoner == null) return;

        global.modifyAllyHealth(ally);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        double distanceToEntity = summoner.getLocation().distance(entity.getLocation());

                        if(ally.getEntity().getTarget() == null){
                            Block block = summoner.getTargetBlock(null, 10);

                            Location blockL = block.getLocation();
                            ally.getEntity().getNavigation().moveTo(blockL.getX(), blockL.getY(), blockL.getZ(), 1.0F);
                        }

                        if(entity.isDead()
                                || summoner.isDead()
                                || (!summoner.hasLineOfSight(entity) && isWithinFOV(summoner, entity))
                                || !isWithinFOV(summoner, entity)
                                || distanceToEntity >= 30.0){
                            nmsEntity.kill((ServerLevel) nmsEntity.level());
                            this.cancel();

                        }
                    }
                }.runTaskTimer(SleepyChronicles.getInstance(), 40, 40);
    }

    private boolean isWithinFOV(Player player, Entity entity) {
        Location playerLoc = player.getEyeLocation();
        Vector playerDir = playerLoc.getDirection().normalize();

        Vector toEntity = entity.getLocation().toVector().subtract(playerLoc.toVector()).normalize();

        double dot = playerDir.dot(toEntity);

        double requiredAngle = 0.55;

        return dot > requiredAngle;
    }

}
