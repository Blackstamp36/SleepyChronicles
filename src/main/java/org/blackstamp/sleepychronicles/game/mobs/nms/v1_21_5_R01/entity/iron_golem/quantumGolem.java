package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.iron_golem;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.util.CraftChatMessage;

public class quantumGolem extends IronGolem {
    public quantumGolem(EntityType<? extends IronGolem> entityType, Level level) {
        super(entityType, level);

        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#70ba6d") + "Quantum Golem"));
        this.addTag("quantumGolem");
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(40);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(35);
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(15);
        this.getAttribute(Attributes.SCALE).setBaseValue(1.5D);
        this.setHealth(35);

        this.goalSelector.getAvailableGoals().clear();

        super.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0F, true));
        super.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9, 32.0F));
        super.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        super.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static void spawnEntity(Location loc, int entities){
        ServerLevel nmsLvl = ((CraftWorld) loc.getWorld()).getHandle();

        for (int i = 0; i < entities; i++) {
            quantumGolem e = new quantumGolem(EntityType.IRON_GOLEM, nmsLvl);
            e.setPos(loc.getX(), loc.getY(), loc.getZ());
            nmsLvl.addFreshEntity(e);

        }

    }
}

