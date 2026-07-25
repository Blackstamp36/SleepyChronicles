package org.blackstamp.sleepychronicles.api.mobs;

import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.game.mobs.custom.bosses.DarknessEmperor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public enum SleepyMobs {

    DARKNESS_EMPEROR("darkness_emperor", DarknessEmperor::new);

    private final String id;
    private final Function<Level, SleepyMob> mob;
    private static final Map<String, Function<Level, SleepyMob>> REGISTRY = new HashMap<>();

    static {
        for(SleepyMobs type : values()){ REGISTRY.put(type.id, type.mob); }
    }

    SleepyMobs(String id, Function<Level, SleepyMob> mob){
        this.id = id;
        this.mob = mob;
    }

    public static Function<Level, SleepyMob> getMob(String id){ return REGISTRY.get(id.toLowerCase()); }

    public static Set<String> getIDs(){ return REGISTRY.keySet(); }
}