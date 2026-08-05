package org.blackstamp.sleepychronicles.api.world;

import org.bukkit.Location;
import org.bukkit.World;

public enum LocationList {

    THRESHOLD_SPAWN(
            WorldType.OVERWORLD.get(),
            0,100,0
    );

    private final Location location;

    LocationList(World world, double x, double y, double z){ this.location = new Location(world,x,y,z); }

    public Location get(){ return this.location; }
}
