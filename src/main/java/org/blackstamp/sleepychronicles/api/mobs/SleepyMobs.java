package org.blackstamp.sleepychronicles.api.mobs;

import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.game.mobs.custom.bosses.DarknessEmperor;

import java.util.function.Function;

public enum SleepyMobs {

    DARKNESS_EMPEROR("darkness_emperor", DarknessEmperor::new);

    private final String id;
    private final Function<Level, SleepyMob> mob;

    SleepyMobs(String id, Function<Level, SleepyMob> mob){
        this.id = id;
        this.mob = mob;

        MobUtils.MOB_REGISTRY.put(id, mob);
    }
}