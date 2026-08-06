package org.blackstamp.sleepychronicles.api.world;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;

@Getter
public enum LocationList {

    THRESHOLD_SPAWN(
            WorldType.OVERWORLD.getWorld(),
            0,100,0
    );

    private final Location location;

    LocationList(World world, double x, double y, double z){ this.location = new Location(world,x,y,z); }

}
