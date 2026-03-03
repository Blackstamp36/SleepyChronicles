package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.creeper;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.summonableMob;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.ally.copyOwnerTarget;
import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
import org.bukkit.craftbukkit.util.CraftChatMessage;

import java.util.UUID;

public class stardustCreeper extends Creeper implements summonableMob {
    @Setter
    @Getter
    private UUID summonerUUID;
    int maxHealth = 20;
    float movSpeed = 0.4F;
    double mobScale = 0.85D;

    public stardustCreeper(EntityType<? extends Creeper> type, Level world) {
        super(type, world);
        this.explosionRadius = 6;
        this.maxSwell = 7;
        this.addTag("stardustCreeper");
        this.addTag("allyMob");
        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#64c7e8") + "Stardust Creeper"));
        this.getAttribute(Attributes.SCALE).setBaseValue(mobScale);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(movSpeed);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
        this.setHealth(this.getMaxHealth());
        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(20);
        this.setSilent(true);

        this.targetSelector.getAvailableGoals().clear();

        this.targetSelector.addGoal(1, new copyOwnerTarget(this, 1.0, true, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Monster.class, true));
    }

    public boolean isSummonable(){
        return true;
    }

    public Mob getEntity(){
        return this;
    }

}


