package org.blackstamp.sleepychronicles.api.dungeon;

import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.api.chat.ChatManager;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.api.mobs.config.DungeonConfig;
import org.blackstamp.sleepychronicles.api.party.SleepyParty;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class RunInstance {
    private final SleepyParty party;
    private final Location center;
    private final double radius;
    private final int timeLimitSeconds;
    private RunState state = RunState.SCAVENGE;

    enum RunState{ SCAVENGE, FIGHTING, BOSS_FIGHT, VICTORY, DEFEAT }

    public RunInstance(SleepyParty party, DungeonType dungeon){
        this.party = party;

        DungeonConfig config = dungeon.getConfig();
        this.center = config.center();
        this.radius = config.radius();
        this.timeLimitSeconds = config.timeLimitSeconds();

        this.checkDistance(); // Initialize the distance task.
    }

    private void checkDistance(){
        new BukkitRunnable(){
            int remainingTicks = timeLimitSeconds * 20; // Time left converted to ticks.
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
                        ChatManager.sendWarning(p,"You don't feel right doing this..",SleepyPalette.MISCELLANEOUS.getColor1());
                    }
                }
            }
        }.runTaskTimer(SleepyChronicles.getInstance(),0L,20L);

    }

    private void spawnBossPortal(){ // Boss spawn logic here..

    }
}
