package org.blackstamp.sleepychronicles.api.mobs.attacks;

import net.minecraft.world.entity.LivingEntity;

public interface SleepyAttack<Mob>{
    void cast(Mob mob, LivingEntity target);
    double getMinDistance();
    double getMaxDistance();
    int getCooldownTicks();
}
