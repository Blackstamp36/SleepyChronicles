package org.blackstamp.sleepychronicles.game.mobs.custom.projectiles;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.global.utils.manager.CollisionManager;
import org.bukkit.Particle;

import java.util.List;

public abstract class BaseProjectile extends ArmorStand {
    private final int damage;
    private final Particle particle;

    private final net.minecraft.world.entity.LivingEntity caster;

    public int ticks;
    private final int lifetime;
    public final int speed;
    private final Vec3 vector;

    public BaseProjectile(EntityType<? extends ArmorStand> entityType, Level level,
                          net.minecraft.world.entity.LivingEntity caster,
                          int damage, Particle particle, int lifetime, int speed){
        super(entityType, level);
        this.damage = damage;
        this.particle = particle;
        this.caster = caster;
        this.lifetime = lifetime;
        this.speed = speed;
        vector = getInitialVector();

        registerAttributes();
    }

    @Override
    public void tick(){
        ticks++;

        if(ticks > lifetime) discard();

        spawnParticle();
        move();
        checkCollisions();
    }

    public Vec3 getInitialVector(){
        org.bukkit.entity.LivingEntity entity = caster.getBukkitLivingEntity();

        final double yaw = Math.toRadians(entity.getYaw());
        final double pitch = Math.toRadians(entity.getPitch());

        final double x = -Math.sin(yaw) * Math.cos(pitch);
        final double y = -Math.sin(pitch);
        final double z = Math.cos(yaw) * Math.cos(pitch);

        return new Vec3(x,y,z).normalize().scale(speed);
    }

    public void move(){
        setDeltaMovement(vector);
    }

    public void registerAttributes(){
        this.setInvisible(true);
        this.setInvulnerable(true);
        this.setSilent(true);
    }

    public void onHit(LivingEntity entity){
        org.bukkit.entity.LivingEntity bukkitEntity = entity.getBukkitLivingEntity();

        bukkitEntity.damage(damage);
    }

    private void checkCollisions(){
        final List<Entity> collisions = CollisionManager.getPlayerCollisions(this);

        if(!collisions.isEmpty())
            for(Entity e : collisions){
                if(!(e instanceof LivingEntity)) continue;

                onHit((LivingEntity) e);
                break;
            }
    }

    public abstract void spawnParticle();
}
