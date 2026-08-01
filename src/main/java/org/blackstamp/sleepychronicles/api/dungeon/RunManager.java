package org.blackstamp.sleepychronicles.api.dungeon;

import net.minecraft.world.entity.ai.attributes.Attributes;
import org.blackstamp.sleepychronicles.api.party.SleepyParty;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RunManager {
    private static final Map<UUID, RunInstance> ACTIVE_RUNS = new HashMap<>();
    private static int runCounter = 0;

    public static void createRun(SleepyParty party, DungeonType dungeon){
        runCounter++;

        double xOffset = runCounter * 1000;
        double radius = 1000D;

        Location center = new Location(Bukkit.getWorld("world_aftermath"),xOffset,100,0);
        RunInstance run = new RunInstance(party,dungeon,center,radius);

        for(UUID uuid : party.getMembers()){ // TODO: add tp logic!
            ACTIVE_RUNS.put(uuid, run);
            Player p = Bukkit.getPlayer(uuid);

            if(p == null || !p.isOnline()){
                return;
            }

            p.teleport(center);
            p.setHealth(p.getAttribute(Attribute.MAX_HEALTH).getValue());
            p.setFoodLevel(20);
            p.setFireTicks(0);

            p.playSound(p.getLocation(), Sound.AMBIENT_BASALT_DELTAS_ADDITIONS,1.0F,1.0F);
        }
    }

    public static RunInstance getRun(UUID uuid){ return ACTIVE_RUNS.get(uuid); }
}
