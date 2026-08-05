package org.blackstamp.sleepychronicles.api.dungeon;

import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.api.chat.ChatManager;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.api.mobs.clone.DownedClone;
import org.blackstamp.sleepychronicles.api.particle.ParticleManager;
import org.blackstamp.sleepychronicles.api.player.PlayerManager;
import org.blackstamp.sleepychronicles.game.mobs.custom.misc.ReviveStand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
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
    private ReviveManager(){}

    // Potion effects for applying/removing debuffs.

    private static final PotionEffect[] DOWNED_DEBUFF = {
            new PotionEffect(PotionEffectType.INVISIBILITY,PotionEffect.INFINITE_DURATION,0,false,false),
            new PotionEffect(PotionEffectType.DARKNESS,PotionEffect.INFINITE_DURATION,0,false,false)
    };
    private static final PotionEffectType[] DOWNED_DEBUFF_TYPES = {
            PotionEffectType.INVISIBILITY,
            PotionEffectType.DARKNESS
    };

    public static void revivePlayer(UUID uuid, RunInstance run){
        Player p = Bukkit.getPlayer(uuid);
        revivePlayer(p,run);
    }

    public static void revivePlayer(Player revivedPlayer, RunInstance run){
        if(revivedPlayer == null || !revivedPlayer.isOnline()) return;
        if(run == null) return;
        if(!PersistentData.has(revivedPlayer, SleepyKeys.IS_DOWNED.get())) return;

        ParticleManager particleManager = new ParticleManager(revivedPlayer.getWorld());
        Location location = revivedPlayer.getLocation();
        String revivedName = revivedPlayer.getName();
        UUID revivedUUID = revivedPlayer.getUniqueId();
        UUID reviveStandUUID = run.getReviveStand(revivedUUID);
        DownedClone clone = run.getDownedClone(revivedUUID);

        PersistentData.remove(revivedPlayer, SleepyKeys.IS_DOWNED.get());
        PlayerManager.clearPots(revivedPlayer, DOWNED_DEBUFF_TYPES);
        run.removeDownedClone(revivedUUID);

        for(UUID memberUUID : run.getParty().getMembers()){
            Player memberPlayer = Bukkit.getPlayer(memberUUID);

            if(memberPlayer == null || !memberPlayer.isOnline()) continue;
            if(clone == null) return;

            ChatManager.sendNotification(memberPlayer,revivedName + " has been revived!");
            clone.unseeFrom(memberPlayer);
            memberPlayer.showPlayer(SleepyChronicles.getInstance(), revivedPlayer);
        }

        if(reviveStandUUID != null){
            run.removeReviveStand(reviveStandUUID);

            Entity standEntity = Bukkit.getEntity(reviveStandUUID);

            if(standEntity != null){ standEntity.remove(); }
        }

        particleManager.particle(
                location, Particle.HAPPY_VILLAGER,null,15,
                0.5D,0.5D,0.5D,
                1.0D
        );
        particleManager.particle(
                location, Particle.TRIAL_SPAWNER_DETECTION_OMINOUS,null,50,
                0.25D,0.25D,0.25D,
                1.0D
        );

        revivedPlayer.setHealth(revivedPlayer.getAttribute(Attribute.MAX_HEALTH).getBaseValue() * 0.36);
        ChatManager.sendWarning(revivedPlayer,"You've been revived!",SleepyPalette.SLEEPY.tag(2,true));
    }

    public static void setDowned(Player downedPlayer, RunInstance run){
        if(downedPlayer == null || !downedPlayer.isOnline()) return;
        if(PersistentData.has(downedPlayer, SleepyKeys.IS_DOWNED.get())) return;

        ParticleManager particleManager = new ParticleManager(downedPlayer.getWorld());
        Location location = downedPlayer.getLocation();
        String downedName = downedPlayer.getName();
        UUID downedUUID = downedPlayer.getUniqueId();
        Level level = ((CraftWorld) location.getWorld()).getHandle();

        PersistentData.set(downedPlayer, SleepyKeys.IS_DOWNED.get(), PersistentDataType.BYTE,(byte) 1);
        run.increaseDownedCount(downedUUID);

        ReviveStand reviveStand = new ReviveStand(level,run,downedUUID);
        double yawRadians = Math.toRadians(location.getYaw());
        double xDir = -Math.sin(yawRadians);
        double zDir = Math.cos(yawRadians);
        double offset = 1.0D;

        double xCentered = location.getBlockX() + 0.5;
        double zCentered = location.getBlockZ() + 0.5;

        double xStand = xCentered - (xDir * offset);
        double zStand = zCentered - (zDir * offset);

        Location standLoc = new Location(location.getWorld(),xStand,location.getY(),zStand);

        level.addFreshEntity(reviveStand, CreatureSpawnEvent.SpawnReason.CUSTOM);
        reviveStand.setPos(xStand, location.getY(), zStand);
        reviveStand.getBukkitEntity().addPassenger(downedPlayer);
        run.addReviveStand(downedUUID, reviveStand.getUUID());

        DownedClone clone = new DownedClone(downedPlayer, standLoc);

        run.addDownedClone(downedUUID,clone);

        for(UUID memberUUID : run.getParty().getMembers()){
            Player memberPlayer = Bukkit.getPlayer(memberUUID);

            if(memberPlayer == null || !memberPlayer.isOnline()) continue;

            clone.showTo(memberPlayer);
            ChatManager.sendNotification(memberPlayer,downedName + " has been downed!");
            memberPlayer.hidePlayer(SleepyChronicles.getInstance(), downedPlayer);
        }

        particleManager.particle(
                location, Particle.END_ROD,null,50,
                0.25D,0.25D,0.25D,
                0.18D
        );

        ChatManager.sendWarning(downedPlayer,"You've been downed!", SleepyPalette.SLEEPY.tag(1,true));
        PlayerManager.addPots(downedPlayer, DOWNED_DEBUFF);
    }
}
