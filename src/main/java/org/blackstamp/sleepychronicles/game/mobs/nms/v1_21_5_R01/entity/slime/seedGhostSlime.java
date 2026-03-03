package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.slime;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.util.CraftChatMessage;

import java.util.Random;

public class seedGhostSlime extends Slime {
    Random r = new Random();

    public seedGhostSlime(EntityType<? extends Slime> type, Level world) {
        super(type, world);

        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#a4c7db") + "Seedghost"));
        this.addTag("seedGhostSlime");
        this.setSize(r.nextInt(1,13),false);

    }

    public static void spawnEntity(Location loc, int entities) {
        ServerLevel nmsLvl = ((CraftWorld) loc.getWorld()).getHandle();

        for (int i = 0; i < entities; i++) {
            seedGhostSlime e = new seedGhostSlime(EntityType.SLIME, nmsLvl);
            e.setPos(loc.getX(), loc.getY(), loc.getZ());
            nmsLvl.addFreshEntity(e);
        }

    }

}
