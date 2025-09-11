package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.ghast;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.bossMob;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.ghast.eodPhase1.*;
import org.blackstamp.sleepyChronicles.util.color.ChatColor;
import org.blackstamp.sleepyChronicles.util.nms.NMSEntity;
import org.bukkit.boss.BarColor;
import org.bukkit.craftbukkit.util.CraftChatMessage;

@NMSEntity
public class emperorOfDarkness extends Ghast implements bossMob {
    @Setter
    @Getter
    private boolean isEnraged = false;

    @Setter
    @Getter
    private int bossPhase = 1;
    @Getter
    @Setter
    private int goalCooldown = 0;

    globalClass global = new globalClass();
    private final int maxHealth = 1000;
    private final int explosionPower = 3;
    private final float flyingSpeed = 1.2F;
    private final double mobScale = 0.75D;
    private int projectileDamage = 24;
    private int projectileCount = 3;
    private String bossName = "Eᴍᴘᴇʀᴏʀ ᴏꜰ Dᴀʀᴋɴᴇꜱꜱ";

    public emperorOfDarkness(EntityType<? extends Ghast> entityType, Level level) {
        super(entityType, level);

        registerAttributes();
        if(isNightTime()) setEnragedMode();
        registerGoals();

        global.initBossBarTask(this, bossName, BarColor.PURPLE, "#62516c");
    }

    public void registerGoals(){
        this.goalSelector.getAvailableGoals().clear();

        this.goalSelector.addGoal(0, new eodHomingProjectilesGoal(this, 8.0, projectileCount, projectileDamage));
        this.goalSelector.addGoal(1, new eodDashGoal(this,8.0));
        this.goalSelector.addGoal(2, new eodDistanceGoal(this,4.0,16.0));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(
                this, Player.class, 10, true, false,
                (entity, _) -> Math.abs(entity.getY() - this.getY()) <= 4.0D));
    }

    private void registerAttributes(){
        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#5e17a1") + "Emperor of Darkness"));

        this.setExplosionPower(explosionPower);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
        this.getAttribute(Attributes.FLYING_SPEED).setBaseValue(flyingSpeed);
        this.getAttribute(Attributes.SCALE).setBaseValue(mobScale);
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
        this.setHealth(this.getMaxHealth());

    }

    private boolean isNightTime(){
        long dayTime = this.getBukkitLivingEntity().getLocation().getWorld().getTime();

        return dayTime >= 13000 && dayTime < 23000;
    }

    private void setEnragedMode(){
        int healthBonus = 500;
        int projectilesToAdd = 2;
        int damageToAdd = 12;

        bossName = "Eɴʀᴀɢᴇᴅ " + bossName;

        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#5e17a1") + "Enraged " + this.getCustomName()));
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.getMaxHealth() + healthBonus);

        projectileDamage+= damageToAdd;
        projectileCount+= projectilesToAdd;
        this.setEnraged(true);
    }

    public void decrementGoalCooldown() {
        if (goalCooldown > 0) goalCooldown--;
    }

    public void increaseGoalCooldown(int value) {
        goalCooldown+= value;
    }

    @Override
    public void tick() {
        super.tick();
        decrementGoalCooldown();
    }

    @Override
    public Mob getEntity() {
        return this;
    }
}
