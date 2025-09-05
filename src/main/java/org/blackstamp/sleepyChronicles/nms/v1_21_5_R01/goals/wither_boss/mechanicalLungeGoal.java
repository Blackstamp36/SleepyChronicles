package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.wither_boss;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepyChronicles.globalClass;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Wither;

import java.util.EnumSet;

public class mechanicalLungeGoal extends Goal {

    globalClass global = new globalClass();
    private final WitherBoss wither;
    private final double lungeSpeed;
    private LivingEntity target;
    private int chargeTime;
    private int cooldown;
    private boolean isCharging;
    private boolean isLunging;
    private int lungeTicks;

    public mechanicalLungeGoal(WitherBoss wither, double lungeSpeed) {
        this.wither = wither;
        this.lungeSpeed = lungeSpeed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.cooldown = 0;
    }

    @Override
    public boolean canUse() {
        if (this.cooldown > 0) {
            this.cooldown--;
            return false;
        }

        if (!this.wither.isAlive()) {
            return false;
        }

        LivingEntity currentTarget = this.wither.getTarget();
        if (currentTarget == null || !currentTarget.isAlive()) {
            return false;
        }

        // Only lunge if target is within 8-15 blocks
        double distance = this.wither.distanceToSqr(currentTarget);
        if (distance < 64.0 || distance > 225.0) { // 8-15 blocks
            return false;
        }

        // 30% chance to lunge when conditions are met
        if (this.wither.getRandom().nextInt(3) != 0) {
            return false;
        }

        this.target = currentTarget;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return (this.isCharging || this.isLunging) &&
                this.wither.isAlive() &&
                this.target != null &&
                this.target.isAlive() &&
                this.lungeTicks < 20; // Max 1 second lunge
    }

    @Override
    public void start() {
        this.isCharging = true;
        this.isLunging = false;
        this.chargeTime = 15;
        this.lungeTicks = 0;

        this.wither.getNavigation().stop();

        global.spawnParticles(this.wither.getBukkitEntity().getLocation(), Particle.ANGRY_VILLAGER, null, 10);

        if(target instanceof Player p){
            org.bukkit.entity.Player bukkitPlayer = (org.bukkit.entity.Player) p.getBukkitEntity();
            bukkitPlayer.playSound(bukkitPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,0.5F);
        }

    }

    @Override
    public void stop() {
        this.isCharging = false;
        this.isLunging = false;
        this.cooldown = 80;
        this.target = null;

        this.wither.setNoAi(false);

        }

    @Override
    public void tick() {
        if (this.target == null) {
            this.stop();
            return;
        }

        if (this.isCharging) {
            handleChargePhase();
        } else if (this.isLunging) {
            handleLungePhase();
        }
    }

    private void handleChargePhase() {
        this.chargeTime--;

        this.wither.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
        this.spawnChargeParticles();

        if (this.chargeTime <= 0) {
            startLunge();
        }
    }

    private void startLunge() {
        this.isCharging = false;
        this.isLunging = true;

        Vec3 direction = calculateLungeDirection();
        Vec3 velocity = direction.scale(this.lungeSpeed);

        this.wither.setDeltaMovement(velocity);
    }

    private void handleLungePhase() { // if player is within a radius it takes damage
        this.lungeTicks++;

        Vec3 direction = calculateLungeDirection();
        Vec3 currentVelocity = this.wither.getDeltaMovement();
        Vec3 newVelocity = currentVelocity.add(direction.scale(0.1)).scale(0.95);
        this.wither.setDeltaMovement(newVelocity);

        spawnLungeParticles();

        if (this.wither.horizontalCollision || this.wither.verticalCollision ||
                this.wither.distanceToSqr(this.target) < 9.0) {
            onLungeImpact();
        }

        if (this.lungeTicks >= 20) {
            this.stop();
        }
    }

    private Vec3 calculateLungeDirection() {
        Vec3 targetPos = this.target.position().add(this.target.getDeltaMovement().scale(5));

        return new Vec3(
                targetPos.x - this.wither.getX(),
                (targetPos.y + this.target.getEyeHeight() * 0.3) - this.wither.getY(),
                targetPos.z - this.wither.getZ()
        ).normalize();
    }

    private void spawnChargeParticles() {
        Wither bukkitWither = (Wither) wither.getBukkitEntity();
        global.spawnParticles(bukkitWither.getLocation(), Particle.CLOUD, null,2);
    }

    private void spawnLungeParticles() {
        Wither bukkitWither = (Wither) wither.getBukkitEntity();
        global.spawnParticles(bukkitWither.getLocation(), Particle.CRIT, null,15);
    }

    private void onLungeImpact() {
        if (this.target != null) {
            this.target.hurt(this.wither.damageSources().mobAttack(this.wither), 25);

            Vec3 knockback = this.wither.getDeltaMovement().normalize().scale(1.5);
            this.target.setDeltaMovement(knockback);
        }

        this.stop();
    }

}