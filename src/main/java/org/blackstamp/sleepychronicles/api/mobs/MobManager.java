package org.blackstamp.sleepychronicles.api.mobs;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.api.mobs.boss.SleepyBosses;
import org.blackstamp.sleepychronicles.api.mobs.npc.SleepyNPCs;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class MobManager {

    private static final Map<String, Function<Level, ? extends Mob>> MOB_REGISTRY = new HashMap<>();

    static{
        for(SleepyMobs mob : SleepyMobs.values()){ MOB_REGISTRY.put(mob.getId(),SleepyMobs.getMob(mob.getId())); }
        for(SleepyBosses boss : SleepyBosses.values()){ MOB_REGISTRY.put(boss.getId(),SleepyMobs.getMob(boss.getId())); }
        for(SleepyNPCs npc : SleepyNPCs.values()){ MOB_REGISTRY.put(npc.getId(),SleepyNPCs.getMob(npc.getId())); }
    }

    public static Set<String> getIDs(){ return MOB_REGISTRY.keySet(); }

    public static @Nullable Mob instantiate(String id, Level level){
        Function<Level,? extends Mob> mob = MOB_REGISTRY.get(id);

        if(mob == null) return null;

        Mob nmsMob = mob.apply(level);

        PersistentData.set(nmsMob.getBukkitEntity(), SleepyKeys.MOB_ID, PersistentDataType.STRING,id);

        return nmsMob;
    }
}