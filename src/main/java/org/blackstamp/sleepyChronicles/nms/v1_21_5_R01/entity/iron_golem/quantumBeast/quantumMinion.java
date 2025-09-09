package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.iron_golem.quantumBeast;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepyChronicles.util.color.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.util.CraftChatMessage;

public class quantumMinion extends Zombie {
    int tickCount = 0;
    int damage = 5;
    int maxHealth = 20;
    float movementSpeed = 0.325F;
    double mobScale = 1.25D;

    public quantumMinion(EntityType<? extends Zombie> type, Level world) {
        super(type, world);

        this.setShouldBurnInDay(false);
        this.setSilent(true);

        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#70ba6d") + "Quantum Minion"));
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(damage);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(movementSpeed);
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(32);
        this.getAttribute(Attributes.SCALE).setBaseValue(mobScale);
        this.setHealth(this.getMaxHealth());

        this.goalSelector.getAvailableGoals().clear();

        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));

        this.targetSelector.getAvailableGoals().clear();

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, null));
    }

    @Override
    public void tick() {
        super.tick();
        this.tickCount++;

        if(tickCount >= 200) if(this.isAlive()) this.kill((ServerLevel) this.level());
    }
}

