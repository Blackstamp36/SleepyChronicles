package org.blackstamp.sleepychronicles.api.mobs;

import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class MobUtils {

    public static @Nullable SleepyMob instantiate(String mob, Level level){
        Function<Level, SleepyMob> sleepy = SleepyMobs.getMob(mob);

        if(sleepy == null) return null;

        return sleepy.apply(level);
    }
}