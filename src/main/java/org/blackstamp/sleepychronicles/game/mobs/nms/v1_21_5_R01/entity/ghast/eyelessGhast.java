package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.ghast;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
import org.blackstamp.sleepychronicles.global.utils.nms.NMSEntity;
import org.bukkit.craftbukkit.util.CraftChatMessage;

@NMSEntity
public class eyelessGhast extends Ghast {
    public eyelessGhast(EntityType<? extends Ghast> entityType, Level level) {
        super(entityType, level);

        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#b9c1c6") + "Eyeless Ghast"));
        this.addTag("eyelessGhast");
        this.setExplosionPower(4);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(35);
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
        this.setHealth(35);

    }
}
