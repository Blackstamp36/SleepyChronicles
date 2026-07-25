package org.blackstamp.sleepychronicles.api.mobs;

import net.minecraft.world.entity.Mob;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftMob;

public class SleepyCraftMob extends CraftMob {

    public SleepyCraftMob(CraftServer server, Mob entity) {
        super(server, entity);
    }
}
