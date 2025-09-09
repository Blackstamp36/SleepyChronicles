package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.zombie;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepyChronicles.item.trinket.trinketItems;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.summonableMob;
import org.blackstamp.sleepyChronicles.util.color.ChatColor;
import org.blackstamp.sleepyChronicles.util.nms.NMSEntity;
import org.bukkit.craftbukkit.util.CraftChatMessage;

import java.util.UUID;

@NMSEntity
public class paleSoul extends Zombie implements summonableMob {
    @Getter
    @Setter
    private UUID summonerUUID;
    private int tickCount = 0;

    public paleSoul(EntityType<? extends Zombie> type, Level world) { // MOB SPAWNS WITHOUT AI
        super(type, world);

        this.setShouldBurnInDay(false);
        this.setSilent(true);

        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#cfc4c3") + "Pale Soul"));
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(12);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.325);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10);
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5);
        this.setHealth(this.getMaxHealth());

        registerGoals();
    }

    @Override
    public void registerGoals() {
        this.goalSelector.getAvailableGoals().clear();
        this.targetSelector.getAvailableGoals().clear();

        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Monster.class, 10, true, false, null));
    }

    @Override
    public void tick() {
        super.tick();
        tickCount++;

        if (tickCount >= 200) killSummon(this);
    }

    public boolean isSummonable() {
        return true;
    }

    public Mob getEntity() {
        return this;
    }

}





