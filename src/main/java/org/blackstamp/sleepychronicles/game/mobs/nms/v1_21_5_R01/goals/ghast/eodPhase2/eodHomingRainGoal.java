package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.ghast.eodPhase2;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.game.mobs.custom.projectiles.types.HomingProjectile;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.ghast.emperorOfDarkness;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class eodHomingRainGoal extends Goal {
    private final Random r = new Random();

    private final emperorOfDarkness entity;
    private final int blockRadius;
    private final int projectileDamage;
    private final int projectileCount;
    private final int tickCooldown;

    public eodHomingRainGoal(emperorOfDarkness entity, int projectileDamage,
                             int blockRadius, int projectileCount, int tickCooldown) {
        this.entity = entity;
        this.blockRadius = blockRadius;
        this.projectileDamage = projectileDamage;
        this.projectileCount = projectileCount;
        this.tickCooldown = tickCooldown;
    }

    @Override
    public boolean canUse() {
        net.minecraft.world.entity.LivingEntity target = entity.getTarget();

        return target != null
                && entity.currentAttack.equals(emperorOfDarkness.phase2Attacks.HOMING_RAIN)
                && entity.getTickCooldown() <= 0;
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
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
        entity.currentAttack = emperorOfDarkness.phase2Attacks.RANDOM_TELEPORT;
    }

    @Override
    public void tick() {
        super.tick();

        LivingEntity target = entity.getTarget();
        if (target == null) return;

        spawnHomingRain(target);
    }

    private void spawnHomingRain(LivingEntity target){
        org.bukkit.entity.Player bukkitT = (org.bukkit.entity.Player) target.getBukkitLivingEntity();
        bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_DEATH,0.85F,1.25F);
        bukkitT.playSound(bukkitT.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE,0.85F,1.25F);
        bukkitT.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,
                20,0, false,false));

        Vec3 startPos = new Vec3(entity.getX(), entity.getY(), entity.getZ());
        Level nmsLevel = entity.level();

        for (int i = 0; i < projectileCount; i++) {
            double angle = r.nextDouble() * 2 * Math.PI;
            double distance = r.nextDouble() * blockRadius;

            double x = startPos.x() + Math.cos(angle) * distance;
            double y = startPos.y() + 30;
            double z = startPos.z() + Math.sin(angle) * distance;

            Vec3 newPos = new Vec3(x, y, z);

            HomingProjectile p = new HomingProjectile(EntityType.ARMOR_STAND, nmsLevel,
                    projectileDamage,70 + (i * 10), 40 + (i * 10),
                    target, entity);

            int delay = r.nextInt(5);

            Bukkit.getScheduler().runTaskLater(SleepyChronicles.getInstance(), () -> {
                p.setPos(newPos);
                nmsLevel.addFreshEntity(p);
            }, delay);
        }
    }
}
