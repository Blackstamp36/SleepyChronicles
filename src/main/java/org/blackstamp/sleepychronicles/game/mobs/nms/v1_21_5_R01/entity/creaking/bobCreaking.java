package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.creaking;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.creaking.Creaking;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
import org.blackstamp.sleepychronicles.global.utils.nms.NMSEntity;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@NMSEntity
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
        this.getBukkitLivingEntity().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,-1,0));
        this.setHealth(1);

    }

}
