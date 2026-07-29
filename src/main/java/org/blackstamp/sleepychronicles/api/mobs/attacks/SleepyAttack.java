package org.blackstamp.sleepychronicles.api.mobs.attacks;

import net.minecraft.world.entity.LivingEntity;
import org.blackstamp.sleepychronicles.api.mobs.SleepyMob;

public interface SleepyAttack<T extends SleepyMob>{
    void cast(T mob, LivingEntity target);
    double getMinDistance();
    double getMaxDistance();
    int getCooldownTicks();
}
