package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.bogged;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.bogged.breezeraBoss;
import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class breezeraPhase1 extends Goal {

    private final breezeraBoss boss;
    private final double minDistance = 1.0;
    private final double maxDistance = 5.0;
    private int shootCooldown = 0;

    public breezeraPhase1(breezeraBoss boss) {
        this.boss = boss;
    }

    @Override
    public boolean canUse() {
        // Can only use this goal when is on phase 1 and there IS a targeted player.
        if(!(boss.getTarget() instanceof Player targetPlayer)) return false;
        org.bukkit.entity.Player bukkitPlayer = (org.bukkit.entity.Player) targetPlayer.getBukkitLivingEntity();

        return boss.getPhase() == 1
                && bukkitPlayer.getGameMode().equals(GameMode.SURVIVAL);
    }

    @Override
    public void tick(){
        CraftEntity bukkitBoss = boss.getBukkitLivingEntity();
        Player p = (Player) boss.getTarget();

        if(p == null) return;

        org.bukkit.entity.Player bukkitPlayer = (org.bukkit.entity.Player) p.getBukkitLivingEntity();
        double actualDistance = bukkitBoss.getLocation().distance(bukkitPlayer.getLocation()); // Distance between the target and the boss

        if (!bukkitPlayer.getGameMode().equals(GameMode.SURVIVAL)) return;

        if(actualDistance < minDistance){ // Boss is too NEAR from its target.
            Vector moveAway = bukkitBoss.getLocation().toVector().subtract(bukkitPlayer.getLocation().toVector()).normalize();
            Location desiredLoc = bukkitBoss.getLocation().add(moveAway).multiply(5);
            boss.getNavigation().moveTo(desiredLoc.getX(), desiredLoc.getY(), desiredLoc.getZ(), 1.0F);

        } else if(actualDistance > maxDistance){ // Boss is too FAR away from its target.
            boss.getNavigation().moveTo(bukkitPlayer.getX(), bukkitPlayer.getY(), bukkitPlayer.getZ(), 1.25F);

        } else {
            boss.getNavigation().stop(); // Stop from navigating, he's in the IDEAL position.
        }

        if(shootCooldown <= 0 && actualDistance >= minDistance && actualDistance <= maxDistance){
            boolean useSpikySeed = boss.getHealth() < (boss.getMaxHealth() * 0.6);
            boolean spikySeed = false;

            if (useSpikySeed && Math.random() < 0.7) spikySeed = true;

            boss.shootSeed(p, spikySeed);

            Bukkit.getScheduler().runTaskLater(sleepyChronicles.getter(), () ->
                    boss.shootSeed(p, false), 5);

            shootCooldown = 30;
        } else {
            shootCooldown--; // Reduce cooldown.
        }
    }
}
