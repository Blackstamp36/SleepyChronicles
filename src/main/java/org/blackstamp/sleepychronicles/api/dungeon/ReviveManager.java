package org.blackstamp.sleepychronicles.api.dungeon;

import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.api.chat.ChatManager;
import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.api.mobs.clone.DownedClone;
import org.blackstamp.sleepychronicles.api.player.PlayerManager;
import org.blackstamp.sleepychronicles.game.mobs.custom.misc.ReviveStand;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class ReviveManager {
    // Potion effects for applying/removing debuffs.

    private static final PotionEffect[] DOWNED_DEBUFF = {
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

    public static void revivePlayer(Player revivedPlayer, RunInstance run){
        if(revivedPlayer == null || !revivedPlayer.isOnline()) return;
        if(run == null) return;
        if(!PersistentData.has(revivedPlayer, SleepyKeys.IS_DOWNED.get())) return;

        UUID playerUUID = revivedPlayer.getUniqueId();
        UUID reviveStandUUID = run.getReviveStand(playerUUID);

        PersistentData.remove(revivedPlayer, SleepyKeys.IS_DOWNED.get());
        PlayerManager.clearPots(revivedPlayer, DOWNED_DEBUFF_TYPES);

        DownedClone clone = run.getDownedClone(playerUUID);

        for(UUID memberUUID : run.getParty().getMembers()){
            Player memberPlayer = Bukkit.getPlayer(memberUUID);

            if(memberPlayer == null || !memberPlayer.isOnline()) continue;

            clone.unseeFrom(memberPlayer);
            memberPlayer.showPlayer(SleepyChronicles.getInstance(), revivedPlayer);
        }

        run.removeDownedClone(playerUUID);

        if(reviveStandUUID != null){
            run.removeReviveStand(reviveStandUUID);

            Entity standEntity = Bukkit.getEntity(reviveStandUUID);

            if(standEntity != null){ standEntity.remove(); }
        }

        revivedPlayer.setHealth(revivedPlayer.getAttribute(Attribute.MAX_HEALTH).getBaseValue() * 0.536);
        ChatManager.sendWarning(revivedPlayer,"You've been revived!",null);
    }

    public static void setDowned(Player downedPlayer, RunInstance run){
        if(PersistentData.has(downedPlayer, SleepyKeys.IS_DOWNED.get())) return;

        UUID playerUUID = downedPlayer.getUniqueId();
        Level level = ((CraftWorld) downedPlayer.getLocation().getWorld()).getHandle();

        PersistentData.set(downedPlayer, SleepyKeys.IS_DOWNED.get(), PersistentDataType.BYTE,(byte) 1);
        run.increaseDownedCount(playerUUID);

        ReviveStand reviveStand = new ReviveStand(level,run, playerUUID);
        run.addReviveStand(playerUUID, reviveStand.getUUID());
        reviveStand.setPos(downedPlayer.getX(), downedPlayer.getY(), downedPlayer.getZ());
        level.addFreshEntity(reviveStand, CreatureSpawnEvent.SpawnReason.CUSTOM);

        DownedClone clone = new DownedClone(downedPlayer, downedPlayer.getLocation());
        run.addDownedClone(playerUUID,clone);

        for(UUID memberUUID : run.getParty().getMembers()){
            Player memberPlayer = Bukkit.getPlayer(memberUUID);

            if(memberPlayer == null || !memberPlayer.isOnline()) continue;

            clone.showTo(memberPlayer);
            memberPlayer.hidePlayer(SleepyChronicles.getInstance(), downedPlayer);
        }

        ChatManager.sendWarning(downedPlayer,"You've been downed!",null);
    }
}
