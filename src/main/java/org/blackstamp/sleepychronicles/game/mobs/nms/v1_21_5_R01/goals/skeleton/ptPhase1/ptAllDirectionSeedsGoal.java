package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.skeleton.ptPhase1;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.armor_stand.unidirectionalSeed;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.skeleton.planterrorBoss;
import org.bukkit.Sound;

public class ptAllDirectionSeedsGoal extends Goal {

    private final planterrorBoss entity;
    private final int lifetimeTicks;
    private final int projectileDamage;
    private final int tickCooldown;

    public ptAllDirectionSeedsGoal(planterrorBoss entity, int lifetimeTicks, int projectileDamage,
                                   int tickCooldown){
        this.entity = entity;
        this.lifetimeTicks = lifetimeTicks;
        this.projectileDamage = projectileDamage;
        this.tickCooldown = tickCooldown;
    }

    @Override
    public boolean canUse() {
        net.minecraft.world.entity.LivingEntity target = entity.getTarget();

        return target != null
                && entity.currentAttack.equals(planterrorBoss.bossAttacks.ALL_DIRECTIONS_SEEDS)
                && entity.getTickCooldown() <= 0;
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public void start() {
        entity.increaseTickCooldown(tickCooldown);
    }

    @Override
    public void stop(){
        entity.currentAttack = planterrorBoss.bossAttacks.SUMMON_MINIONS;
    }

    @Override
    public void tick(){
        LivingEntity target = entity.getTarget();
        if(target == null) return;

        fireSeeds();
    }

    private void fireSeeds(){
        entity.teleportTo(entity.getX(), entity.getY(), entity.getZ());

        for(org.bukkit.entity.Player nearby : entity.getBukkitLivingEntity().getLocation().getNearbyPlayers(16)){
            nearby.playSound(nearby.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL,0.85F,1.75F);
            nearby.playSound(nearby.getLocation(), Sound.ENTITY_WITHER_SPAWN,0.85F,1.25F);
            nearby.playSound(nearby.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR,0.85F,1.75F);
        }
        for(double i = 0; i < 360; i+= 45) sendSingleSeed(i);
    }

    private void sendSingleSeed(double degrees){
        Level nmsLevel = entity.level();

        unidirectionalSeed seed = new unidirectionalSeed(EntityType.ARMOR_STAND, nmsLevel,
                projectileDamage,lifetimeTicks + (int) (degrees * 0.5), entity, degrees);

        seed.setPos(entity.getX(), entity.getY(), entity.getZ());
        nmsLevel.addFreshEntity(seed);
    }
}
