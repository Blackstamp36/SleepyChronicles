package org.blackstamp.sleepychronicles.api.mobs;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class MobUtils {

    public static @Nullable Mob instantiate(String mob, Level level){
        Function<Level,? extends Mob> sleepy = SleepyMobs.getMob(mob);

        if(sleepy == null) return null;

        return sleepy.apply(level);
    }
}