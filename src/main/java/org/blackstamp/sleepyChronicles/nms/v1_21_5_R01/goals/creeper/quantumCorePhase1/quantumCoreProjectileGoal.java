package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.creeper.quantumCorePhase1;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumCore;
import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;

public class quantumCoreProjectileGoal extends Goal {

    int attackCooldown = 0;
    int burstCount = 0;

    private final quantumCore entity;

    public quantumCoreProjectileGoal(quantumCore entity) {
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        if(!(entity.getTarget() instanceof Player targetPlayer)) return false;

        org.bukkit.entity.Player bukkitPlayer = (org.bukkit.entity.Player) targetPlayer.getBukkitLivingEntity();
        LivingEntity bukkitEntity = entity.getBukkitLivingEntity();
        double distanceToTarget = bukkitEntity.getLocation().distance(bukkitPlayer.getLocation());

        return distanceToTarget >= 7.0
                && distanceToTarget < 18.0
                && bukkitEntity.hasLineOfSight(bukkitPlayer)
                && bukkitPlayer.getGameMode().equals(GameMode.SURVIVAL);
    }

    @Override
    public void tick() {
        Player targetPlayer = (Player) entity.getTarget();
        if (targetPlayer == null) return;

        if(attackCooldown >= 1) {
            attackCooldown--;
            return;
        }

        if(burstCount < 5){
            entity.shootQuantumProjectile(targetPlayer);
            burstCount++;
            attackCooldown = 6;

        } else {
            burstCount = 0;
            attackCooldown = 20;
        }
    }
}

