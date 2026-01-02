package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.skeleton.ptPhase1;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.armor_stand.pinkSeed;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.skeleton.planterrorBoss;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class ptPinkSeedsGoal extends Goal {

    private final planterrorBoss entity;
    private final int projectileDamage;
    private final int tickCooldown;
    private final int projectileCount;

    public ptPinkSeedsGoal(planterrorBoss entity, int projectileDamage,
                           int projectileCount, int tickCooldown) {
        this.entity = entity;
        this.projectileDamage = projectileDamage;
        this.projectileCount = projectileCount;
        this.tickCooldown = tickCooldown;
    }

    @Override
    public boolean canUse() {
        net.minecraft.world.entity.LivingEntity target = entity.getTarget();

        return target != null
                && entity.currentAttack.equals(planterrorBoss.bossAttacks.PINK_SEEDS)
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
        entity.currentAttack = planterrorBoss.bossAttacks.POISON_ATTACK;
    }

    @Override
    public void tick(){
        LivingEntity target = entity.getTarget();
        if(target == null) return;

        shootPinkSeed(entity);
    }

    private void shootPinkSeed(Mob entity){
        org.bukkit.entity.Player bukkitT = (org.bukkit.entity.Player) entity.getTarget().getBukkitLivingEntity();
        bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS,0.5F,1.25F);

        Level nmsLevel = entity.level();

        new BukkitRunnable(){
            int tickCount = 0;
            int projectilesShot = 0;

            @Override
            public void run(){
                tickCount++;

                if(projectilesShot >= projectileCount || tickCount >= 120) this.cancel();

                if(tickCount % 20 == 0) {
                    projectilesShot++;

                    if(entity.getTarget() == null) this.cancel();

                    pinkSeed projectile = new pinkSeed(EntityType.ARMOR_STAND, nmsLevel,
                            projectileDamage, 40 + (int) (tickCount * 0.75), entity.getTarget(), entity);

                    projectile.setPos(entity.position());
                    nmsLevel.addFreshEntity(projectile);

                    bukkitT.playSound(bukkitT.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,0.15F,0.75F);
                }
            }
        }.runTaskTimer(SleepyChronicles.getInstance(), 0, 1);

    }
}
