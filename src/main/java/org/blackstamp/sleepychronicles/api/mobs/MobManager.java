package org.blackstamp.sleepychronicles.api.mobs;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.api.mobs.boss.SleepyBosses;
import org.blackstamp.sleepychronicles.api.mobs.npc.SleepyNPCs;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class MobManager {
    private MobManager(){}

    private static final Map<String, Function<Level, ? extends Mob>> MOB_REGISTRY = new HashMap<>();

    static{
        // Sleepy Mobs.
        for(SleepyMobs mob : SleepyMobs.values()) {
            String id = mob.getId();

            MOB_REGISTRY.put(id,SleepyMobs.getMob(id));
        }
        // Bosses.
        for(SleepyBosses boss : SleepyBosses.values()) {
            String id = boss.getId();

            MOB_REGISTRY.put(id,SleepyBosses.getBoss(id));
        }
        // NPCs.
        for(SleepyNPCs npc : SleepyNPCs.values()) {
            String id = npc.getId();

            MOB_REGISTRY.put(id,SleepyNPCs.getNPC(id));
        }
    }

    public static Set<String> getIDs() {
        return MOB_REGISTRY.keySet();
    }

    public static @NotNull Mob instantiate(String id, Level level) {
        Function<Level,? extends Mob> mob = MOB_REGISTRY.get(id);

        Mob nmsMob = mob.apply(level);

        PersistentData.set(nmsMob.getBukkitEntity(), SleepyKeys.MOB_ID.get(), PersistentDataType.STRING,id);

        return nmsMob;
    }
}