package org.blackstamp.sleepychronicles.game.mobs.custom.projectiles.types;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.game.mobs.custom.projectiles.ProjectileSettings;

public class HomingProjectile extends LinearProjectile {
    private final LivingEntity target;

    private final int delay;

    public HomingProjectile(Level level, LivingEntity caster, ProjectileSettings settings,
                            LivingEntity target, int delay) {
        super(level, caster, settings, null);
        this.target = target;
        this.delay = delay;
    }

    @Override
    public void move(){
        if(!(super.ticks >= delay)) return;

        Vec3 originVec = this.position();
        Vec3 targetVec = target.position();
        Vec3 desiredVec = targetVec.subtract(originVec).normalize().scale(settings.speed);

        Vec3 currentVec = getDeltaMovement();
        Vec3 finalVec = currentVec.lerp(desiredVec,0.1);

        setDeltaMovement(finalVec.normalize().scale(super.settings.speed));
    }
}