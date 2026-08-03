package org.blackstamp.sleepychronicles.api.dungeon;

import lombok.Getter;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.api.chat.ChatManager;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.api.mobs.MobManager;
import org.blackstamp.sleepychronicles.api.mobs.config.DungeonConfig;
import org.blackstamp.sleepychronicles.api.party.SleepyParty;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class RunInstance {
    @Getter public final SleepyParty party;

    public BukkitTask timeTask = null;

    private UUID bossUUID = null;

    private final String bossID;
    private final Location center;
    private final double radius;
    private final int timeLimitSeconds;
    private RunState state = RunState.SCAVENGE;

    private final List<UUID> reviveStands = new ArrayList<>();
    private final Map<UUID,Integer> downedCount = new HashMap<>();

    enum RunState{ SCAVENGE, FIGHTING, BOSS_FIGHT, VICTORY, DEFEAT }

    public RunInstance(SleepyParty party, DungeonType dungeon){
        this.party = party;

        DungeonConfig config = dungeon.getConfig();
        this.bossID = config.bossId();
        this.center = config.center();
        this.radius = config.radius();
        this.timeLimitSeconds = config.timeLimitSeconds();

        this.checkDistance(); // Initialize the distance task.
    }

    // Distance task.
    private void checkDistance(){
        this.timeTask = new BukkitRunnable(){
            int remainingTicks = timeLimitSeconds * 20; // Time left converted to ticks.
            final double radiusSquared = radius * radius;

            @Override
            public void run(){
                if(remainingTicks > 0) remainingTicks--;
                if(remainingTicks <= 0 && state == RunState.SCAVENGE){
                    spawnBossPortal(center);
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

    private void spawnBossPortal(Location location){ // Boss spawn logic here..
        Level level = ((CraftWorld) location.getWorld()).getHandle();

        Mob boss = MobManager.instantiate(bossID, level);

        if(boss == null){
            SleepyChronicles.getInstance().getLogger().warning("An error has occurred upon summoning the boss!");
            return;
        }

        UUID bossUUID = boss.getUUID();

        RunManager.addBossInstance(bossUUID,this);
        this.bossUUID = bossUUID;

        boss.setPos(location.x(),location.y(),location.z());
        level.addFreshEntity(boss);
    }

    public void cleanupRun(boolean isVictory){
        if(this.timeTask != null && !this.timeTask.isCancelled()){ this.timeTask.cancel(); }

        this.clearReviveStands();

        if(this.bossUUID != null && isVictory){
            Entity boss = Bukkit.getEntity(this.bossUUID);

            if(boss != null) boss.remove();
        }

        RunManager.clearBossInstance(this.bossUUID);
        for(UUID member : this.party.getMembers()){ RunManager.removeRunInstance(member); }
    }

    public void increaseDownedCount(UUID uuid){ this.downedCount.put(uuid, this.getDownedCount(uuid) + 1); }
    public int getDownedCount(UUID uuid){ return this.downedCount.getOrDefault(uuid,0); }

    public void addReviveStand(UUID uuid){ this.reviveStands.add(uuid); }
    public void removeReviveStand(UUID uuid){ this.reviveStands.remove(uuid); }

    public void clearReviveStands(){
        for(UUID uuid : reviveStands){
            Entity stand = Bukkit.getEntity(uuid);

            if(stand != null) stand.remove();
        }
        this.reviveStands.clear();
    }
}
