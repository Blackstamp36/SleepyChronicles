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

public class suppressedCreeper extends Creeper {

    public suppressedCreeper(EntityType<? extends Creeper> type, Level world) {
        super(type, world);

        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#567f86") + "Suppressed"));
        this.explosionRadius = 4;
        this.maxSwell = 10;
        this.addTag("suppressedCreeper");
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.35);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(25);
        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(20);
        this.getAttribute(Attributes.SCALE).setBaseValue(1.5);
        this.setHealth(25);

    }

    public static void spawnEntity(Location loc, int entities) {
        ServerLevel nmsLvl = ((CraftWorld) loc.getWorld()).getHandle();

        for (int i = 0; i < entities; i++) {
            suppressedCreeper e = new suppressedCreeper(EntityType.CREEPER, nmsLvl);
            e.setPos(loc.getX(), loc.getY(), loc.getZ());
            nmsLvl.addFreshEntity(e);

        }

    }
}
