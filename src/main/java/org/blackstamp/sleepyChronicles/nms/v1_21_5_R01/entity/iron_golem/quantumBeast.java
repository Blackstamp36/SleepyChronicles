package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.iron_golem;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.util.CraftChatMessage;

import java.util.UUID;

public class quantumBeast extends IronGolem {
    int damage = 20;
    int maxHealth = 5;
    float movementSpeed = 0.95F;
    double mobScale = 3.5D;

    public quantumBeast(EntityType<? extends IronGolem> entityType, Level level) {
        super(entityType, level);

        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#70ba6d") + "Quantum Beast"));
        this.addTag("quantumBeast");
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(damage);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(movementSpeed);
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(20);
        this.getAttribute(Attributes.SCALE).setBaseValue(mobScale);
        this.setHealth(35);

        this.goalSelector.getAvailableGoals().clear();

        super.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0F, true));
        super.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9, 32.0F));
        super.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        super.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    public static void spawnEntity(Location loc, int entities){
        ServerLevel nmsLvl = ((CraftWorld) loc.getWorld()).getHandle();

        for (int i = 0; i < entities; i++) {
            quantumBeast e = new quantumBeast(EntityType.IRON_GOLEM, nmsLvl);
            e.setPos(loc.getX(), loc.getY(), loc.getZ());
            nmsLvl.addFreshEntity(e);

        }

    }
}

