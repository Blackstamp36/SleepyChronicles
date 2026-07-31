package org.blackstamp.sleepychronicles.api.dungeon;

import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.api.chat.ChatManager;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.api.party.SleepyParty;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class RunInstance {
    private final SleepyParty party;
    private final DungeonType dungeon;
    private final Location center;
    private final double radius;
    private RunState state = RunState.SCAVENGE;

    enum RunState{ SCAVENGE, FIGHTING, BOSS_FIGHT, VICTORY, DEFEAT }

    public RunInstance(SleepyParty party, DungeonType dungeon, Location center, double radius){
        this.party = party;
        this.dungeon = dungeon;
        this.center = center;
        this.radius = radius;

        this.checkDistance(); // Initialize the distance task.
    }

    private void checkDistance(){
        new BukkitRunnable(){
            int remainingTicks = 43200; // 36 minutes in ticks.
            final double radiusSquared = radius * radius;

            @Override
            public void run(){
                if(remainingTicks > 0) remainingTicks--;
                if(remainingTicks <= 0 && state == RunState.SCAVENGE){
                    spawnBossPortal();
                    state = RunState.BOSS_FIGHT;
                }

                for(UUID uuid : party.getMembers()){
                    Player p = Bukkit.getPlayer(uuid);

                    if(p == null) continue;

                    Location l = p.getLocation();

                    if(l.getWorld() != center.getWorld()) continue;

                    if(l.distanceSquared(center) > radiusSquared){
                        p.damage(4);
                        ChatManager.sendWarning(p,"Get.. back..",SleepyPalette.MISCELLANEOUS.getColor1());
                    }
                }
            }
        }.runTaskTimer(SleepyChronicles.getInstance(),0L,20L);

    }

    private void spawnBossPortal(){ // Boss spawn logic here..

    }
}
