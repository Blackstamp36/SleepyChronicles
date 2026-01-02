package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.ghast;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.bossMob;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.ghast.eodPhase1.*;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.ghast.eodPhase2.eodHomingRainGoal;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.ghast.eodPhase2.eodLevitationSpellGoal;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.ghast.eodPhase2.eodRandomTeleportGoal;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.ghast.eodPhase2.eodSupernovaGoal;
import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
import org.blackstamp.sleepychronicles.global.utils.nms.NMSEntity;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

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
    private int tickCooldown = 0;

    GlobalClass global = new GlobalClass();
    private final int maxHealth = 1000;
    private final double mobScale = 0.55D;
    private int projectileDamage = 24;
    private int projectileCount = 3;
    private int supernovaDamage = 100;
    private int sphereDamage = 50;
    private String bossName = "Eᴍᴘᴇʀᴏʀ ᴏꜰ Dᴀʀᴋɴᴇꜱꜱ";

    @Getter
    public enum phase2Attacks {
        HOMING_RAIN,
        LEVITATION_SPELL,
        RANDOM_TELEPORT,
        SUPERNOVA
        }

    public phase2Attacks currentAttack = phase2Attacks.SUPERNOVA;

    private final String bossThemeKey = "sleepy.boss.emperor_of_darkness.phase_1";
    private final int bossThemeDuration = 56;

    public emperorOfDarkness(EntityType<? extends Ghast> entityType, Level level) {
        super(entityType, level);

        registerAttributes();
        if(isNightTime()) setEnragedMode();
        registerGoals();

        global.initBossBarTask(this, bossName, BarColor.PURPLE, "#5e17a1");
    }

    public void registerGoals(){
        this.goalSelector.getAvailableGoals().clear();

        this.goalSelector.addGoal(0, new eodHomingProjectilesGoal(this,6.0,
                projectileCount,projectileDamage,90));
        this.goalSelector.addGoal(1, new eodDashGoal(this,8.0, 100));
        this.goalSelector.addGoal(2, new eodDistanceGoal(this,4.0,12.0));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(
                this, Player.class, 10, true, false,
                (entity, _) -> Math.abs(entity.getY() - this.getY()) <= 4.0D));
    }

    private void registerAttributes(){
        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#5e17a1") + "Emperor of Darkness"));

        this.setSilent(true);

        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
        this.getAttribute(Attributes.SCALE).setBaseValue(mobScale);
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
        this.setHealth(this.getMaxHealth());
    }

    public void initSecondPhase(emperorOfDarkness entity){
        if(entity.getBossPhase() == 2) return;

        entity.setBossPhase(2);

        LivingEntity bukkitE = entity.getBukkitLivingEntity();
        Location l = bukkitE.getLocation();
        bukkitE.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE,-1,1));

        l.getWorld().playSound(l, Sound.ENTITY_GHAST_DEATH, 1.0F, 0.75F);

        l.getWorld().getNearbyEntities(l, 5, 5, 5).forEach(e -> {
            if (e instanceof org.bukkit.entity.Player p) {

                Vector direction = p.getLocation().toVector().subtract(l.toVector()).normalize();
                p.setVelocity(direction.multiply(1.5).setY(0.7));
            }
        });

        entity.goalSelector.getAvailableGoals().clear();

        entity.goalSelector.addGoal(0, new eodHomingRainGoal(this,
                projectileDamage,25,projectileCount * 7,60));
        entity.goalSelector.addGoal(1, new eodSupernovaGoal(this, supernovaDamage, 50));
        entity.goalSelector.addGoal(2, new eodRandomTeleportGoal(this, 16,sphereDamage,50));
        entity.goalSelector.addGoal(3, new eodLevitationSpellGoal(this,10,projectileDamage,50));
        entity.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(
                this, Player.class, 10, true, false,
                (_, _) -> Math.abs(entity.getY() - this.getY()) <= 4.0D));
    }

    private boolean isNightTime(){
        long dayTime = this.getBukkitLivingEntity().getLocation().getWorld().getTime();

        return dayTime >= 13000 && dayTime < 23000;
    }

    private void setEnragedMode(){
        int healthBonus = 1000;
        int projectilesToAdd = 3;
        int damageToAdd = 12;
        int sphereDamageToAdd = 15;

        bossName = "Eɴʀᴀɢᴇᴅ " + bossName;

        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#5e17a1") + "Enraged Emperor of Darkness"));
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.getMaxHealth() + healthBonus);

        projectileDamage+= damageToAdd;
        projectileCount+= projectilesToAdd;
        sphereDamage+= sphereDamageToAdd;
        this.setEnraged(true);
    }

    public void decrementTickCooldown() {
        if(tickCooldown > 0) tickCooldown--;
    }

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
    public void tick() {
        super.tick();
        decrementTickCooldown();
    }

    public Mob getEntity() {
        return this;
    }
}
