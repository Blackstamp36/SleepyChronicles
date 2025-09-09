package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.endermite;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepyChronicles.util.color.ChatColor;
import org.blackstamp.sleepyChronicles.util.nms.NMSEntity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.util.CraftChatMessage;

@NMSEntity
public class netherMite extends Endermite {

    public netherMite(EntityType<? extends Endermite> type, Level world) {
        super(type, world);

        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#5b332a") + "Nethermite"));
        this.addTag("netherMite");
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(10);
        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(10);
        this.setHealth(10);

    }

    public static void spawnEntity(Location loc, int entities) {
        ServerLevel nmsLvl = ((CraftWorld) loc.getWorld()).getHandle();

        for (int i = 0; i < entities; i++) {
            netherMite e = new netherMite(EntityType.ENDERMITE, nmsLvl);
            e.setPos(loc.getX(), loc.getY(), loc.getZ());
            nmsLvl.addFreshEntity(e);
        }

    }

}
