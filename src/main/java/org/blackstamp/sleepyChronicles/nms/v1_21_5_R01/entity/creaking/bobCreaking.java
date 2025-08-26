package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.creaking;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.creaking.Creaking;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.util.CraftChatMessage;

public class bobCreaking extends Creaking {

    public bobCreaking(EntityType<? extends Creaking> type, Level world) {
        super(type, world);

        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#59504f") + "Bob"));
        this.addTag("bobCreaking");
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(50);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.55);
        this.getAttribute(Attributes.SCALE).setBaseValue(1.3);
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(32);
        this.setHealth(1);

    }

    public static void spawnEntity(Location loc, int entities) {
        ServerLevel nmsLvl = ((CraftWorld) loc.getWorld()).getHandle();

        for (int i = 0; i < entities; i++){
            bobCreaking e = new bobCreaking(EntityType.CREAKING, nmsLvl);
            e.setPos(loc.getX(), loc.getY(), loc.getZ());
            nmsLvl.addFreshEntity(e);
        }


    }

}
