package org.blackstamp.sleepychronicles.game.mobs.custom.projectiles.types;

import com.destroystokyo.paper.ParticleBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.game.mobs.custom.projectiles.ProjectileSettings;
import org.blackstamp.sleepychronicles.global.utils.manager.CollisionManager;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LinearProjectile extends ArmorStand {
    protected final ProjectileSettings settings;
    protected int ticks;

    private final LivingEntity caster;
    private final Vec3 vector;
    private final ParticleBuilder builder;

    public LinearProjectile(Level level, LivingEntity caster, ProjectileSettings settings, @Nullable Vec3 vector){
        super(EntityType.ARMOR_STAND, level);
        this.caster = caster;
        this.settings = settings;
        this.builder = new ParticleBuilder(settings.particle);

        if(vector == null) this.vector = getInitialVector();
        else this.vector = vector;

        registerAttributes();
    }

    @Override
    public void tick(){
        super.tick();
        ticks++;

        if(ticks > settings.lifetime) discard();

        spawnParticle(builder);
        move();
        checkCollisions();
    }

    public Vec3 getInitialVector(){
        final double yaw = Math.toRadians(this.caster.getYRot());
        final double pitch = Math.toRadians(this.caster.getXRot());

        final double x = -Math.sin(yaw) * Math.cos(pitch);
        final double y = -Math.sin(pitch);
        final double z = Math.cos(yaw) * Math.cos(pitch);

        return new Vec3(x,y,z).normalize().scale(settings.speed);
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
        if(settings.explosionRadius >= 1.0){
            Vec3 pos = entity.position();

            this.level().explode(entity, pos.x, pos.y, pos.z, settings.explosionRadius, false, Level.ExplosionInteraction.MOB);
        }

        if(this.level() instanceof ServerLevel level){
            entity.hurtServer(level, this.damageSources().mobAttack(this.caster), settings.damage);
        }

        org.bukkit.entity.LivingEntity bukkitEntity = entity.getBukkitLivingEntity();

        bukkitEntity.damage(settings.damage, this.caster.getBukkitLivingEntity());
    }

    private void checkCollisions(){
        final List<Entity> collisions = CollisionManager.getPlayerCollisions(this);

        if(!collisions.isEmpty())
            for(Entity e : collisions){
                if(!(e instanceof LivingEntity)) continue;

                onHit((LivingEntity) e);
                if(this.isAlive()) this.discard();
                break;
            }
    }

    public void spawnParticle(ParticleBuilder builder){
        builder.location(this.level().getWorld(), this.getX(), this.getY(), this.getZ())
                .spawn();
    }
}
