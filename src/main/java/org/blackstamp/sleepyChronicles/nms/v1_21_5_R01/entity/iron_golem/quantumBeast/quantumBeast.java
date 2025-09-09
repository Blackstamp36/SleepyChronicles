package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.iron_golem.quantumBeast;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.iron_golem.quantumBeastPhase1.quantumBeastBarracudaGoal;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.iron_golem.quantumBeastPhase1.quantumBeastShockWaveGoal;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.iron_golem.quantumBeastPhase2.quantumBeastBoostGoal;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.iron_golem.quantumBeastPhase2.quantumBeastCrushGoal;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.iron_golem.quantumBeastPhase2.quantumBeastSmashBlocksGoal;
import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.color.ChatColor;
import org.blackstamp.sleepyChronicles.util.nms.NMSEntity;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

@NMSEntity
public class quantumBeast extends IronGolem {
    @Setter
    @Getter
    public quantumCore core;
    @Setter
    @Getter
    int bossPhase = 1;
    int damage = 5;
    int maxHealth = 1;
    float movementSpeed = 0.325F;
    double mobScale = 3.25D;
    private int tickCount = 0;

    public quantumBeast(EntityType<? extends IronGolem> entityType, Level level) {
        super(entityType, level);

        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#70ba6d") + "Quantum Beast"));
        this.addTag("quantumBeast");
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(damage);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(movementSpeed);
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(32);
        this.getAttribute(Attributes.SCALE).setBaseValue(mobScale);
        this.setHealth(this.getMaxHealth());

        registerGoals();
    }

    @Override
    protected void registerGoals(){
        this.goalSelector.getAvailableGoals().clear();
        this.targetSelector.getAvailableGoals().clear();

        this.goalSelector.addGoal(0, new quantumBeastBarracudaGoal(this));
        this.goalSelector.addGoal(1, new quantumBeastShockWaveGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0F, true));
        this.goalSelector.addGoal(3, new MoveTowardsTargetGoal(this, 0.9, 32.0F));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void tick() {
        super.tick();

        if (!core.isAlive()) this.kill((ServerLevel) this.level());

        }

    public void initSecondPhase(quantumBeast entity){
        if(getBossPhase() == 2) return;

        this.setBossPhase(2);

        Entity bukkitE = entity.getBukkitEntity();
        Location l = bukkitE.getLocation();

        l.getWorld().playSound(l, Sound.ENTITY_RAVAGER_ROAR, 1.0F, 0.75F);

        l.getWorld().getNearbyEntities(l, 5, 5, 5).forEach(e -> {
            if (e instanceof org.bukkit.entity.Player p) {

                Vector direction = p.getLocation().toVector().subtract(l.toVector()).normalize();
                p.setVelocity(direction.multiply(1.5).setY(0.7));
            }
        });

        entity.goalSelector.getAvailableGoals().clear();

        entity.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.0F, true));
        entity.goalSelector.addGoal(1, new MoveTowardsTargetGoal(this, 0.9, 32.0F));
        entity.goalSelector.addGoal(2, new quantumBeastCrushGoal(entity));
        entity.goalSelector.addGoal(3, new quantumBeastBoostGoal(entity));
        entity.goalSelector.addGoal(4, new quantumBeastSmashBlocksGoal(entity, 4));

    }
}

