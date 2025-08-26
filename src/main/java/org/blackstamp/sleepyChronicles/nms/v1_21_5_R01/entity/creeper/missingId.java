package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.creeper;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.util.CraftChatMessage;

public class missingId extends Creeper {

    public missingId(EntityType<? extends Creeper> type, Level world) {
        super(type, world);

        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#db1fdb") + "missingId"));
        this.explosionRadius = 3;
        this.maxSwell = 15;
        this.addTag("missingId");
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(5);
        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(20);
        this.setHealth(5);

    }

    public static void spawnEntity(Location loc, int entities) {
        ServerLevel nmsLvl = ((CraftWorld) loc.getWorld()).getHandle();

        for (int i = 0; i < entities; i++){
            missingId e = new missingId(EntityType.CREEPER, nmsLvl);
            e.setPos(loc.getX(), loc.getY(), loc.getZ());
            nmsLvl.addFreshEntity(e);

            }
    }

}
