package org.blackstamp.sleepychronicles.api.dungeon;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.chat.ChatManager;
import org.blackstamp.sleepychronicles.api.chat.ChatPrefix;
import org.blackstamp.sleepychronicles.api.mobs.MobManager;
import org.blackstamp.sleepychronicles.api.party.SleepyParty;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RunManager {
    private RunManager(){}

    // Active maps.
    private static final Map<UUID, RunInstance> ACTIVE_RUNS = new HashMap<>();
    private static final Map<UUID, RunInstance> ACTIVE_BOSSES = new HashMap<>();

    // Active bosses.
    public static void addBossInstance(UUID uuid, RunInstance run){ ACTIVE_BOSSES.put(uuid, run); }
    public static RunInstance getBossInstance(UUID uuid){ return ACTIVE_BOSSES.get(uuid); }
    public static void clearBossInstance(UUID uuid){ ACTIVE_BOSSES.remove(uuid); }

    // Active runs.
    public static RunInstance getRunInstance(UUID uuid){ return ACTIVE_RUNS.get(uuid); }
    public static void removeRunInstance(UUID uuid){ ACTIVE_RUNS.remove(uuid); }
    public static boolean isNotInRun(UUID uuid){ return !ACTIVE_RUNS.containsKey(uuid); }

    private static int runCounter = 0;

    public static void createRun(SleepyParty party, DungeonType dungeon){
        runCounter++;

        Location center = new Location(Bukkit.getWorld("world_aftermath"), runCounter * 1000, 100, 0);
        RunInstance run = new RunInstance(party,dungeon,center);
        Player leader = Bukkit.getPlayer(party.getLeader());

        if(leader == null || !leader.isOnline()) return;

        for(UUID memberUUID : party.getMembers()){
            Player memberPlayer = Bukkit.getPlayer(memberUUID);
            if(memberPlayer == null || !memberPlayer.isOnline()){
                ChatManager.sendMessage(leader,"Unable to start the run because a member is offline.", ChatPrefix.ERROR);

                return;
            }
        }

        for(UUID memberUUID : party.getMembers()){
            Player memberPlayer = Bukkit.getPlayer(memberUUID);

            ACTIVE_RUNS.put(memberUUID, run);

            memberPlayer.teleport(center);
            memberPlayer.setHealth(memberPlayer.getAttribute(Attribute.MAX_HEALTH).getValue());
            memberPlayer.setFoodLevel(20);
            memberPlayer.setFireTicks(0);

            memberPlayer.playSound(memberPlayer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1.0F,1.25F);
        }

        // TEST ONLY!!
        generateTestFloor(center);
        spawnBoss(leader, dungeon.getConfig().bossId(), center);
    }

    private static void generateTestFloor(Location center){
        Location floor = center.clone().subtract(0, 1, 0);

        for(int x = -5; x <= 5; x++){
            for(int z = -5; z <= 5; z++){
                floor.clone().add(x, 0, z).getBlock().setType(org.bukkit.Material.DEEPSLATE_BRICKS);
            }
        }
    }

    private static void spawnBoss(Player p, String bossID, Location location){
        Level level = ((CraftWorld) location.getWorld()).getHandle();

        Mob entity = MobManager.instantiate(bossID, level);

        if(entity == null){
            ChatManager.sendMessage(p, "There was an error upon summoning the boss!");
            return;
        }

        entity.setPos(location.x() + 5,location.y(),location.z());
        level.addFreshEntity(entity);

        ChatManager.sendMessage(p, "Summoning boss..", ChatPrefix.STAFF);
    }
}
