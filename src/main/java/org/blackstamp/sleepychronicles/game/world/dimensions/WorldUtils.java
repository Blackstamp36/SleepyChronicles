package org.blackstamp.sleepychronicles.game.world.dimensions;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Objects;

@Getter
public enum WorldUtils {

    OVERWORLD(Objects.requireNonNull(Bukkit.getWorld("world")).getSpawnLocation()),
    NETHER(Objects.requireNonNull(Bukkit.getWorld("world_nether")).getSpawnLocation()),
    END(Objects.requireNonNull(Bukkit.getWorld("world_the_end")).getSpawnLocation()),
    THE_AFTERMATH(Objects.requireNonNull(Bukkit.getWorld("world_aftermath")).getSpawnLocation());

    private final Location location;

    WorldUtils(Location location) { this.location = location; }
}
