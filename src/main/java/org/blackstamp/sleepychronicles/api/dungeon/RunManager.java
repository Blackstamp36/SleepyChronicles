package org.blackstamp.sleepychronicles.api.dungeon;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.chat.ChatManager;
import org.blackstamp.sleepychronicles.api.mobs.MobManager;
import org.blackstamp.sleepychronicles.api.mobs.config.DungeonConfig;
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
    private static final Map<UUID, RunInstance> ACTIVE_RUNS = new HashMap<>();
    private static final Map<UUID, RunInstance> ACTIVE_BOSSES = new HashMap<>();
    private static int runCounter = 0;

    public static void registerBoss(UUID uuid, RunInstance run){ ACTIVE_BOSSES.put(uuid, run); }
    public static RunInstance getBoss(UUID uuid){ return ACTIVE_BOSSES.get(uuid); }
    public static void clearBoss(UUID uuid){ ACTIVE_BOSSES.remove(uuid); }

    public static void createRun(SleepyParty party, DungeonType dungeon){
        runCounter++;

        Location center = new Location(Bukkit.getWorld("world_aftermath"), 1000 * runCounter, 100, 0);

        DungeonConfig config = dungeon.getConfig();
        RunInstance run = new RunInstance(party,dungeon);

        Player leader = Bukkit.getPlayer(party.getLeader());

        for(UUID uuid : party.getMembers()){
            Player p = Bukkit.getPlayer(uuid);

            if(p == null || !p.isOnline()){
                ChatManager.sendMessage(leader, true, "Unable to start the run because a member is offline.");
                return;
            }

            ACTIVE_RUNS.put(uuid, run);

            p.teleport(center);
            p.setHealth(p.getAttribute(Attribute.MAX_HEALTH).getValue());
            p.setFoodLevel(20);
            p.setFireTicks(0);

            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1.0F,1.25F);
        }

        // TEST ONLY!!
        generateTestFloor(center);
        spawnBoss(leader, config.bossId(), center);
    }

    public static RunInstance getRun(UUID uuid){ return ACTIVE_RUNS.get(uuid); }
    public static boolean isInRun(UUID uuid){ return ACTIVE_RUNS.containsKey(uuid); }

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
            ChatManager.sendStaffMessage(p, "There was an error upon summoning the boss!");
            return;
        }

        entity.setPos(location.x() + 5,location.y(),location.z());
        level.addFreshEntity(entity);

        ChatManager.sendStaffMessage(p, "Summoning boss..");
    }
}
