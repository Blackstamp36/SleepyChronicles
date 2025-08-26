package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.fox;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.util.CraftChatMessage;

public class kitsuneFox extends Fox {

    public kitsuneFox(EntityType<? extends Fox> type, Level world) {
        super(type, world);

        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#e4ced1") + "Kitsune"));
        this.addTag("kitsuneFox");
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(15);
        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(10);
        this.setHealth(30);

        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static void spawnEntity(Location loc, int entities) {
        ServerLevel nmsLvl = ((CraftWorld) loc.getWorld()).getHandle();

        for (int i = 0; i < entities; i++) {
            kitsuneFox e = new kitsuneFox(EntityType.FOX, nmsLvl);
            e.setPos(loc.getX(), loc.getY(), loc.getZ());
            nmsLvl.addFreshEntity(e);
        }
    }
}
