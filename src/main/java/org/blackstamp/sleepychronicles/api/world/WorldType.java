package org.blackstamp.sleepychronicles.api.world;

import org.bukkit.Bukkit;
import org.bukkit.World;

public enum WorldType {

    OVERWORLD(Bukkit.getWorld("world")),
    NETHER(Bukkit.getWorld("world_nether")),
    END(Bukkit.getWorld("world_the_end")),
    AFTERMATH(Bukkit.getWorld("world_aftermath"));

    private final World world;

    WorldType(World world){ this.world = world; }

    public World get(){ return this.world; }
}
