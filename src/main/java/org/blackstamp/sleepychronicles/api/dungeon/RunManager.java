package org.blackstamp.sleepychronicles.api.dungeon;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.chat.ChatManager;
import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.api.mobs.MobManager;
import org.blackstamp.sleepychronicles.api.mobs.config.DungeonConfig;
import org.blackstamp.sleepychronicles.api.party.SleepyParty;
import org.blackstamp.sleepychronicles.api.player.PlayerManager;
import org.blackstamp.sleepychronicles.game.mobs.custom.misc.ReviveStand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RunManager {
    private static final Map<UUID, RunInstance> ACTIVE_RUNS = new HashMap<>();
    private static final Map<UUID, RunInstance> ACTIVE_BOSSES = new HashMap<>();
    private static int runCounter = 0;

    public static void addBossInstance(UUID uuid, RunInstance run){ ACTIVE_BOSSES.put(uuid, run); }
    public static RunInstance getBossInstance(UUID uuid){ return ACTIVE_BOSSES.get(uuid); }
    public static void clearBossInstance(UUID uuid){ ACTIVE_BOSSES.remove(uuid); }

    private static final PotionEffectType[] DOWNED_DEBUFF_TYPES = {
            PotionEffectType.SLOWNESS,
            PotionEffectType.DARKNESS,
            PotionEffectType.GLOWING
    };

    private static final PotionEffect[] DOWNED_DEBUFF = {
            new PotionEffect(PotionEffectType.SLOWNESS,PotionEffect.INFINITE_DURATION,4),
            new PotionEffect(PotionEffectType.DARKNESS,PotionEffect.INFINITE_DURATION,0),
            new PotionEffect(PotionEffectType.GLOWING,PotionEffect.INFINITE_DURATION,0)
    };

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

    public static RunInstance getRunInstance(UUID uuid){ return ACTIVE_RUNS.get(uuid); }
    public static void removeRunInstance(UUID uuid){ ACTIVE_RUNS.remove(uuid); }
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

    public static void revivePlayer(UUID uuid){
        Player p = Bukkit.getPlayer(uuid);
        revivePlayer(p);
    }

    public static void revivePlayer(Player p){


        if(p == null || !p.isOnline()) return;
        if(!PersistentData.has(p,SleepyKeys.IS_DOWNED.get())) return;

        PersistentData.remove(p, SleepyKeys.IS_DOWNED.get());
        PlayerManager.clearPots(p, DOWNED_DEBUFF_TYPES);

        p.setHealth(p.getAttribute(Attribute.MAX_HEALTH).getBaseValue() * 0.3);
        p.setPose(Pose.STANDING);
        ChatManager.sendWarning(p,"You've been revived!",null);
    }

    public static void setDowned(Player p, RunInstance run){ // Execute downed logic..
        if(PersistentData.has(p, SleepyKeys.IS_DOWNED.get())) return;

        PersistentData.set(p, SleepyKeys.IS_DOWNED.get(), PersistentDataType.BYTE,(byte) 1);

        UUID uuid = p.getUniqueId();
        Level level = ((CraftWorld) p.getLocation().getWorld()).getHandle();

        run.increaseDownedCount(uuid);

        ReviveStand reviveStand = new ReviveStand(level,run,uuid);

        level.addFreshEntity(reviveStand, CreatureSpawnEvent.SpawnReason.CUSTOM);

        ChatManager.sendWarning(p,"You've been downed!",null);
        p.setPose(Pose.SLEEPING);
        PlayerManager.addPots(p, DOWNED_DEBUFF);
    }
}
