package org.blackstamp.sleepychronicles.game.mobs.custom.projectiles;

import lombok.Getter;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.game.mobs.custom.projectiles.types.HomingProjectile;
import org.blackstamp.sleepychronicles.game.mobs.custom.projectiles.types.LinearProjectile;
import org.bukkit.Particle;
import org.jetbrains.annotations.Nullable;

@Getter
public enum SleepyProjectiles {

    DARK_SPARK(new ProjectileSettings(Particle.DRAGON_BREATH, 0.0F, 50, 1,30));

    private final ProjectileSettings settings;

    SleepyProjectiles(ProjectileSettings settings){ this.settings = settings; }

    public void shootLinear(Level level, LivingEntity caster, Vec3 pos, @Nullable Vec3 vector){
        LinearProjectile projectile = new LinearProjectile(level, caster, settings, vector);
        projectile.setPos(pos);
        level.addFreshEntity(projectile);
    }

    public void shootHoming(Level level, LivingEntity caster, Vec3 pos, LivingEntity target, int delay){
        HomingProjectile projectile = new HomingProjectile(level, caster, settings, target, delay);
        projectile.setPos(pos);
        level.addFreshEntity(projectile);
    }
}