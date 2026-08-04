package org.blackstamp.sleepychronicles.api.mobs.clone;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.api.dungeon.RunInstance;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.UUID;

public class DownedClone {

    private final GameProfile fakeProfile;
    private final ServerPlayer fakePlayer;
    private final BlockPos location;

    public DownedClone(Player clonedPlayer, Location location){
        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();
        ServerPlayer nmsOriginal = ((CraftPlayer) clonedPlayer).getHandle();
        GameProfile originalProfile = nmsOriginal.getGameProfile();

        double yawRadians = Math.toRadians(location.getYaw());

        int targetX = location.getBlockX() - (int) Math.round(Math.sin(yawRadians));
        int targetZ = location.getBlockZ() + (int) Math.round(Math.cos(yawRadians));

        this.location = new BlockPos(targetX,location.getBlockY(),targetZ);
        this.fakeProfile = new GameProfile(UUID.randomUUID(), "");
        this.fakeProfile.getProperties().putAll(originalProfile.getProperties());
        this.fakePlayer = new ServerPlayer(level.getServer(), level, this.fakeProfile, nmsOriginal.clientInformation());

        this.fakePlayer.setPos(location.x(),location.y(),location.z());
        this.fakePlayer.setYRot(location.getYaw());
        this.fakePlayer.setXRot(location.getPitch());
        this.fakePlayer.setPose(Pose.SLEEPING);
        this.fakePlayer.setSleepingPos(this.location);
    }

    public void showTo(Player p){
        ServerPlayer nmsPlayer = ((org.bukkit.craftbukkit.entity.CraftPlayer) p).getHandle();

        ClientboundPlayerInfoUpdatePacket.Entry fakeEntry = new ClientboundPlayerInfoUpdatePacket.Entry(
                this.fakePlayer.getUUID(),
                this.fakeProfile,
                true,
                0,
                GameType.SURVIVAL,
                null,
                false,
                0,
                null
        );

        ClientboundPlayerInfoUpdatePacket infoUpdatePacket = new ClientboundPlayerInfoUpdatePacket(
                java.util.EnumSet.of(
                        ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER,
                        ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LISTED
                ),
                Collections.singletonList(fakeEntry)
        );

        ClientboundAddEntityPacket addEntityPacket = new ClientboundAddEntityPacket(
                this.fakePlayer.getId(), this.fakePlayer.getUUID(),
                this.fakePlayer.getX(), this.fakePlayer.getY(), this.fakePlayer.getZ(),
                this.fakePlayer.getXRot(), this.fakePlayer.getYRot(),
                EntityType.PLAYER,
                0,
                Vec3.ZERO,
                this.fakePlayer.getYHeadRot()
        );

        ClientboundSetEntityDataPacket dataPacket = new ClientboundSetEntityDataPacket(
                this.fakePlayer.getId(),
                this.fakePlayer.getEntityData().getNonDefaultValues()
        );

        nmsPlayer.connection.send(infoUpdatePacket);

        Bukkit.getScheduler().runTaskLater(SleepyChronicles.getInstance(), () -> {
            nmsPlayer.connection.send(addEntityPacket);
            nmsPlayer.connection.send(dataPacket);
        }, 5);
    }

    public void unseeFrom(Player p){
        ServerPlayer nmsPlayer = ((org.bukkit.craftbukkit.entity.CraftPlayer) p).getHandle();

        ClientboundPlayerInfoRemovePacket infoRemovePacket = new ClientboundPlayerInfoRemovePacket(
                Collections.singletonList(this.fakePlayer.getUUID()
                ));

        ClientboundRemoveEntitiesPacket removeEntitiesPacket = new ClientboundRemoveEntitiesPacket(this.fakePlayer.getId());

        nmsPlayer.connection.send(infoRemovePacket);
        nmsPlayer.connection.send(removeEntitiesPacket);
    }
}
