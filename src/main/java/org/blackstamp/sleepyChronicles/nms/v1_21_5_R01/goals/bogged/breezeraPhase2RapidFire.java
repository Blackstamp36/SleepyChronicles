package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.bogged;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.bogged.breezeraBoss;
import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;

public class breezeraPhase2RapidFire extends Goal {
    int attackCooldown = 0;
    int burstCount = 0;

    private final breezeraBoss boss;

    public breezeraPhase2RapidFire(breezeraBoss boss) {
        this.boss = boss;
    }

    @Override
    public boolean canUse() {
        if(!(boss.getTarget() instanceof Player targetPlayer)) return false;
        org.bukkit.entity.Player bukkitPlayer = (org.bukkit.entity.Player) targetPlayer.getBukkitLivingEntity();
        LivingEntity bukkitBoss = boss.getBukkitLivingEntity();
        double distanceToTarget = bukkitBoss.getLocation().distance(bukkitPlayer.getLocation());

        return distanceToTarget >= 7.0
                && distanceToTarget < 14.0
                && boss.getPhase() == 2
                && bukkitPlayer.getGameMode().equals(GameMode.SURVIVAL);
    }

    @Override
    public void tick() {
        Player targetPlayer = (Player) boss.getTarget();
        if (targetPlayer == null) return;

        org.bukkit.entity.Player bukkitPlayer = (org.bukkit.entity.Player) targetPlayer.getBukkitLivingEntity();

        if (!bukkitPlayer.getGameMode().equals(GameMode.SURVIVAL)) return;

        if(attackCooldown >= 1) {
            attackCooldown--;
            return;
        }

        if(burstCount < 5){
            boss.shootSeed(targetPlayer, true);
            burstCount++;
            attackCooldown = 3;

        } else {
            burstCount = 0;
            attackCooldown = 20;
        }


    }
}
