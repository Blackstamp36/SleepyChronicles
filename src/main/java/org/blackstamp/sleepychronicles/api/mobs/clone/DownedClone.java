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
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.UUID;

public class DownedClone {
    // TODO:
    // 1. check how to move the downed clone more to the center (so it's more intuitive to where to attack)
    // 2. teleport to the overworld if it's not in a run. exactly to the lobby (but if there's no schem, then don't do anything)
    // 3. add the /p leave command.. if not.... we'll be all doomed
    // 5. add listeners to prevent the 'downed' player from breaking things!

    private final GameProfile fakeProfile;
    private final ServerPlayer fakePlayer;
    private final BlockPos location;

    public DownedClone(Player clonedPlayer, Location location){
        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();
        ServerPlayer nmsOriginal = ((CraftPlayer) clonedPlayer).getHandle();
        GameProfile originalProfile = nmsOriginal.getGameProfile();

        this.location = new BlockPos(location.getBlockX(),location.getBlockY(),location.getBlockZ());
        this.fakeProfile = new GameProfile(UUID.randomUUID(), "");
        this.fakeProfile.getProperties().putAll(originalProfile.getProperties());
        this.fakePlayer = new ServerPlayer(level.getServer(), level, this.fakeProfile, nmsOriginal.clientInformation());

        this.fakePlayer.setPos(location.x(),location.y(),location.z());
        this.fakePlayer.setYRot(location.getYaw());
        this.fakePlayer.setXRot(location.getPitch());
        this.fakePlayer.setPose(Pose.SLEEPING);
        this.fakePlayer.setSleepingPos(this.location);
        this.fakePlayer.setGlowingTag(true);
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
