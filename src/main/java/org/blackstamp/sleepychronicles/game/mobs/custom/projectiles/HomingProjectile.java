package org.blackstamp.sleepychronicles.game.mobs.custom.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.api.particle.ParticleManager;
import org.bukkit.Particle;

public class HomingProjectile extends BaseProjectile {
    private final org.bukkit.entity.LivingEntity bukkitEntity;

    private final LivingEntity target;

    private final int delay;

    private final ParticleManager particleManager;

    public HomingProjectile(EntityType<? extends ArmorStand> entityType, Level level,
                            Particle particle,
                            int damage, int lifetime, int delay, int speed,
                            LivingEntity target, LivingEntity caster) {
        super(entityType, level, caster, damage, particle, lifetime, speed);
        this.target = target;
        this.delay = delay;
        this.bukkitEntity = this.getBukkitLivingEntity();
        this.particleManager = new ParticleManager(bukkitEntity.getWorld());
    }

    @Override
    public void move(){
        if(!(ticks >= delay)) return;

        Vec3 originVec = this.position();
        Vec3 targetVec = target.position();
        Vec3 desiredVec = targetVec.subtract(originVec).normalize().scale(speed);

        Vec3 currentVec = getDeltaMovement();
        Vec3 finalVec = currentVec.lerp(desiredVec,0.1);

        setDeltaMovement(finalVec.normalize().scale(speed));
    }

    @Override
    public void spawnParticle() {
        particleManager.particle(bukkitEntity.getLocation(), Particle.SQUID_INK, null,
                4,0.05,0.35,0.05,0.25);
    }
}