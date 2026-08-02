package org.blackstamp.sleepychronicles.api.dungeon;

import org.blackstamp.sleepychronicles.api.party.PartyManager;
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

        Location center = dungeon.getCenter();
        RunInstance run = new RunInstance(party,dungeon,center,dungeon.getRadius());

        for(UUID uuid : party.getMembers()){
            ACTIVE_RUNS.put(uuid, run);
            Player p = Bukkit.getPlayer(uuid);

            if(p == null || !p.isOnline()){ // Remove player from party and all - logic.
                ACTIVE_RUNS.remove(uuid);

                party.removeMember(uuid);
                PartyManager.removeFromParty(uuid);

                continue;
            }

            p.teleport(center);
            p.setHealth(p.getAttribute(Attribute.MAX_HEALTH).getValue());
            p.setFoodLevel(20);
            p.setFireTicks(0);

            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1.0F,1.25F);
        }
    }

    public static RunInstance getRun(UUID uuid){ return ACTIVE_RUNS.get(uuid); }
    public static boolean isInRun(UUID uuid){ return ACTIVE_RUNS.containsKey(uuid); }
}
