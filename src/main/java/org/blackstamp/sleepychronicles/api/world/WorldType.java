package org.blackstamp.sleepychronicles.api.world;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;

public enum WorldType {

    OVERWORLD("world"),
    NETHER("world_nether"),
    END("world_the_end"),
    AFTERMATH("world_aftermath");

    @Getter private final String worldName;

    private World world;

    WorldType(String worldName) {
        this.worldName = worldName;
    }

    public World getWorld() {
        if(this.world == null) this.world = Bukkit.getWorld(worldName);

        return world;
    }
}
