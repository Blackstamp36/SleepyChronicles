package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.iron_golem.quantumBeast;

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
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.iron_golem.quantumBeastPhase1.qbEarthquakeGoal;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.iron_golem.quantumBeastPhase1.qbHomingWavesGoal;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.iron_golem.quantumBeastPhase2.qbSpeedBoostGoal;
import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
import org.blackstamp.sleepychronicles.global.utils.nms.NMSEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

@NMSEntity
public class quantumBeast extends IronGolem {
    @Setter
    @Getter
    private quantumCore core;
    @Setter
    @Getter
    private int bossPhase = 1;
    @Getter
    int tickCount = 0;
    @Getter
    @Setter
    int tickCooldown = 0;

    public enum bossAttacks{
        HOMING_WAVES,
        EARTHQUAKE,
        SPEED_BOOST
    }

    public quantumBeast.bossAttacks currentAttack = bossAttacks.HOMING_WAVES;

    private final int attackDamage = 30;
    private final int maxHealth = 1;
    private final float movementSpeed = 0.325F;
    private final double mobScale = 2.85D;
    private final int projectileDamage = 40;
    private int projectileCount = 2;

    public quantumBeast(EntityType<? extends IronGolem> entityType, Level level) {
        super(entityType, level);

        registerGoals();
        registerAttributes();
    }

    public void registerGoals() {
        this.goalSelector.getAvailableGoals().clear();
        this.targetSelector.getAvailableGoals().clear();

        this.goalSelector.addGoal(0, new qbHomingWavesGoal(this,
                projectileDamage, projectileCount,80));
        this.goalSelector.addGoal(1, new qbEarthquakeGoal(this,
                projectileDamage * 2, 40,60));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0F, true));
        this.goalSelector.addGoal(4, new MoveTowardsTargetGoal(this, 0.9, 32.0F));

        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    private void registerAttributes(){
        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#70ba6d") + "Quantum Beast"));

        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(attackDamage);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(movementSpeed);
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(32);
        this.getAttribute(Attributes.SCALE).setBaseValue(mobScale);
        this.setHealth(this.getMaxHealth());
    }

    public void initSecondPhase(quantumBeast entity){
        if(entity.getBossPhase() == 2) return;

        entity.setBossPhase(2);

        this.goalSelector.addGoal(2, new qbSpeedBoostGoal(this,
                3,100));

        Entity bukkitE = entity.getBukkitEntity();
        Location l = bukkitE.getLocation();

        l.getWorld().playSound(l, Sound.ENTITY_RAVAGER_ROAR, 1.0F, 0.75F);

        l.getWorld().getNearbyEntities(l, 5, 5, 5).forEach(e -> {
            if (e instanceof org.bukkit.entity.Player p) {

                Vector direction = p.getLocation().toVector().subtract(l.toVector()).normalize();
                p.setVelocity(direction.multiply(1.5).setY(0.7));
            }
        });
    }

    @Override
    public void tick(){
        super.tick();

        decrementTickCooldown();
        if(!core.isAlive()) this.kill((ServerLevel) this.level());

        getCoreTeam().addEntity(this.getBukkitLivingEntity());
    }

    private Team getCoreTeam(){
        Scoreboard manager = Bukkit.getScoreboardManager().getMainScoreboard();
        Team greenTeam = manager.getTeam("greenTeam");

        if(greenTeam != null) return greenTeam;

        Team team = manager.registerNewTeam("greenTeam");
        team.setColor(org.bukkit.ChatColor.GREEN);

        return team;
    }

    public void decrementTickCooldown() {
        if(tickCooldown > 0) tickCooldown--;
    }

    public void increaseTickCooldown(int value) {
        tickCooldown+= value;
    }

}

