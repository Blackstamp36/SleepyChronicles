package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.creeper.quantumCorePhase2;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumCore;
import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.manager.CollisionManager;
import org.blackstamp.sleepyChronicles.util.manager.ParticleManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class quantumCoreEnhancedProjectileGoal extends Goal {
    CollisionManager cM = new CollisionManager();

    int attackCooldown = 0;
    int burstCount = 0;

    private final quantumCore entity;

    public quantumCoreEnhancedProjectileGoal(quantumCore entity) {
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        if(!(entity.getTarget() instanceof Player targetPlayer)) return false;

        org.bukkit.entity.Player bukkitPlayer = (org.bukkit.entity.Player) targetPlayer.getBukkitLivingEntity();
        LivingEntity bukkitEntity = entity.getBukkitLivingEntity();
        double distanceToTarget = bukkitEntity.getLocation().distance(bukkitPlayer.getLocation());

        return distanceToTarget < 8.0
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

        if(burstCount < 10){
            entity.shootQuantumProjectile(targetPlayer);
            burstCount++;
            attackCooldown = 3;

        } else {
            burstCount = 0;
            attackCooldown = 20;
        }
    }
}

