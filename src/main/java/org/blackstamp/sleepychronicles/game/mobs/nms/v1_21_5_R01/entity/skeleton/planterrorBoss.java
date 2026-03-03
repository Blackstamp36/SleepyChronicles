package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.skeleton;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.bossMob;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.skeleton.ptPhase1.*;
import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.craftbukkit.util.CraftChatMessage;

public class planterrorBoss extends Skeleton implements bossMob {
    @Setter
    @Getter
    private int bossPhase = 1;
    @Getter
    int tickCount = 0;
    @Getter
    @Setter
    int tickCooldown = 0;

    @Getter
    @Setter
    String bossName = "Pʟᴀɴᴛᴇʀʀᴏʀ";

    public enum bossAttacks{
        PINK_SEEDS,
        POISON_ATTACK,
        ALL_DIRECTIONS_SEEDS,
        SUMMON_MINIONS,
    }

    public planterrorBoss.bossAttacks currentAttack = bossAttacks.PINK_SEEDS;

    GlobalClass global = new GlobalClass();
    private final int maxHealth = 750;
    private final double mobScale = 1.65D;
    private final int projectileDamage = 15;
    private final int projectileCount = 4;
    private final int minDistanceDamage = 16;

    private final String bossThemeKey = "sleepy.boss.planterror.phase_1";
    private final int bossThemeDuration = 224;

    public planterrorBoss(EntityType<? extends Skeleton> entityType, Level level) {
        super(entityType, level);

        registerGoals();
        registerAttributes();
       }

    public void registerGoals() {
        this.goalSelector.getAvailableGoals().clear();

        this.goalSelector.addGoal(0, new ptPinkSeedsGoal(this,
                projectileDamage, projectileCount,80));
        this.goalSelector.addGoal(1, new ptPoisonAttackGoal(this,
                projectileDamage * 2, 300,40));
        this.goalSelector.addGoal(2, new ptAllDirectionSeedsGoal(this,
                80, projectileDamage,40));
        this.goalSelector.addGoal(3, new ptSummonMinionsGoal(this,
                4,80));
        this.goalSelector.addGoal(4, new ptAmbushGoal(this,
                20.0,10));
        this.goalSelector.addGoal(5, new ptDistanceGoal(this,
                3.0,12.0, minDistanceDamage));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    private void registerAttributes(){
        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#5f940c") + "Planterror"));

        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
        this.getAttribute(Attributes.SCALE).setBaseValue(mobScale);
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
        this.setHealth(this.getMaxHealth());
        this.setSilent(true);
        this.setShouldBurnInDay(false);

        this.getWeaponItem().asBukkitMirror().setType(Material.AIR);

        global.initBossBarTask(this, bossName, BarColor.GREEN, "#5f940c");
    }

    @Override
    public void aiStep(){ // view why doesn't "tick" or idk.
        super.aiStep();

        decrementTickCooldown();
    }

    @Override
    public void decrementTickCooldown() {
        if(tickCooldown > 0) tickCooldown--;
    }

    @Override
    public void increaseTickCooldown(int value) {
        tickCooldown+= value;
    }

    @Override
    public int getThemeDurationTicks() {
        return bossThemeDuration * 20;
    }

    @Override
    public String getBossTheme() {
        return bossThemeKey;
    }

    @Override
    public Mob getEntity() {
        return this;
    }
}