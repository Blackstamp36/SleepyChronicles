package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.creeper.quantumCorePhase2;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumCore;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumMinion;
import org.blackstamp.sleepyChronicles.util.manager.ParticleManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.CraftWorld;

public class quantumCoreMinionGoal extends Goal {

    private final quantumCore entity;

    int minionCooldown = 0;

    public quantumCoreMinionGoal(quantumCore entity) {
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        if(!(entity.getTarget() instanceof Player targetPlayer)) return false;

        org.bukkit.entity.Player bukkitPlayer = (org.bukkit.entity.Player) targetPlayer.getBukkitLivingEntity();
        org.bukkit.entity.LivingEntity bukkitEntity = entity.getBukkitLivingEntity();
        double distanceToTarget = bukkitEntity.getLocation().distance(bukkitPlayer.getLocation());

        return distanceToTarget >= 8.0
                && !bukkitEntity.hasLineOfSight(bukkitPlayer)
                && bukkitPlayer.getGameMode().equals(GameMode.SURVIVAL);
    }

    @Override
    public void tick() {
        Player targetPlayer = (Player) entity.getTarget();
        if (targetPlayer == null) return;

        if (minionCooldown >= 1) {
            minionCooldown--;
            return;
        }

        // Summons 2 core minions.
        for(int i = 0; i < 2; i++) summonQuantumMinion(entity.getBukkitEntity().getLocation());
        minionCooldown = 120;
    }

    private void summonQuantumMinion(Location l){
        ParticleManager pM = new ParticleManager(l.getWorld());
        Vec3 vec3 = new Vec3(l.getX(), l.getY(), l.getZ());
        Level nmsLevel = ((CraftWorld) l.getWorld()).getHandle();
        quantumMinion entity = new quantumMinion(EntityType.ZOMBIE, nmsLevel);
        nmsLevel.addFreshEntity(entity);
        entity.setPos(vec3);
        pM.spawnParticle(l, Particle.SOUL,null,
                25,0.25,0.5,0.25, 1.0);

    }
}
