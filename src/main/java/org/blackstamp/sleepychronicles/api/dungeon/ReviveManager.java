package org.blackstamp.sleepychronicles.api.dungeon;

import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.chat.ChatManager;
import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.api.player.PlayerManager;
import org.blackstamp.sleepychronicles.game.mobs.custom.misc.ReviveStand;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class ReviveManager {

    private static final PotionEffect[] DOWNED_DEBUFF = {
            new PotionEffect(PotionEffectType.INVISIBILITY,PotionEffect.INFINITE_DURATION,0),
            new PotionEffect(PotionEffectType.SLOWNESS,PotionEffect.INFINITE_DURATION,9),
            new PotionEffect(PotionEffectType.DARKNESS,PotionEffect.INFINITE_DURATION,0),
            new PotionEffect(PotionEffectType.GLOWING,PotionEffect.INFINITE_DURATION,0)
    };
    private static final PotionEffectType[] DOWNED_DEBUFF_TYPES = {
            PotionEffectType.SLOWNESS,
            PotionEffectType.DARKNESS,
            PotionEffectType.GLOWING
    };

    public static void revivePlayer(UUID uuid, RunInstance run){
        Player p = Bukkit.getPlayer(uuid);
        revivePlayer(p,run);
    }

    public static void revivePlayer(Player p, RunInstance run){
        if(p == null || !p.isOnline()) return;
        if(run == null) return;
        if(!PersistentData.has(p, SleepyKeys.IS_DOWNED.get())) return;

        UUID playerUUID = p.getUniqueId();
        UUID reviveStandUUID = run.getReviveStand(playerUUID);

        PersistentData.remove(p, SleepyKeys.IS_DOWNED.get());
        PlayerManager.clearPots(p, DOWNED_DEBUFF_TYPES);

        if(reviveStandUUID != null){
            run.removeReviveStand(reviveStandUUID);

            Entity standEntity = Bukkit.getEntity(reviveStandUUID);

            if(standEntity != null){ standEntity.remove(); }
        }

        p.setHealth(p.getAttribute(Attribute.MAX_HEALTH).getBaseValue() * 0.3);
        p.setPose(Pose.STANDING);
        ChatManager.sendWarning(p,"You've been revived!",null);
    }

    public static void setDowned(Player p, RunInstance run){
        if(PersistentData.has(p, SleepyKeys.IS_DOWNED.get())) return;

        UUID uuid = p.getUniqueId();
        Level level = ((CraftWorld) p.getLocation().getWorld()).getHandle();

        PersistentData.set(p, SleepyKeys.IS_DOWNED.get(), PersistentDataType.BYTE,(byte) 1);
        run.increaseDownedCount(uuid);

        ReviveStand reviveStand = new ReviveStand(level,run,uuid);
        reviveStand.setPos(p.getX(),p.getY(),p.getZ());
        level.addFreshEntity(reviveStand, CreatureSpawnEvent.SpawnReason.CUSTOM);

        ChatManager.sendWarning(p,"You've been downed!",null);
        p.setPose(Pose.SLEEPING);
        PlayerManager.addPots(p, DOWNED_DEBUFF);
    }
}
