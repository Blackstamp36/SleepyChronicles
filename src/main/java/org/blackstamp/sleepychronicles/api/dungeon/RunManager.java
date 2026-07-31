package org.blackstamp.sleepychronicles.api.dungeon;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RunManager {
    private static final Map<UUID, RunInstance> ACTIVE_RUNS = new HashMap<>();
    private static int runCounter = 0;

    public static void createRun(SleepyParty party, String bossID){
        runCounter++;

        double xOffset = runCounter * 1000;
        double radius = 1000D;

        Location center = new Location(Bukkit.getWorld("world_aftermath"),xOffset,100,0);
        RunInstance run = new RunInstance(party,bossID,center,radius);

        for(UUID uuid : party.getMembers()){  }
    }

    public static RunInstance getRun(UUID uuid){ return ACTIVE_RUNS.get(uuid); }
}
