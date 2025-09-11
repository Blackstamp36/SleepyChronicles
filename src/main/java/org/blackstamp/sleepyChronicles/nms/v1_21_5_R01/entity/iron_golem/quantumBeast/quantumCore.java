package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.iron_golem.quantumBeast;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.bossMob;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.creeper.quantumCorePhase1.quantumCoreProjectileGoal;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.creeper.quantumCorePhase2.quantumCoreEnhancedProjectileGoal;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.creeper.quantumCorePhase2.quantumCoreMinionGoal;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.iron_golem.quantumBeastPhase2.quantumBeastBoostGoal;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.iron_golem.quantumBeastPhase2.quantumBeastCrushGoal;
import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.color.ChatColor;
import org.blackstamp.sleepyChronicles.util.manager.ParticleManager;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

public class quantumCore extends Creeper implements bossMob {
    @Setter
    @Getter
    private quantumBeast owner;
    @Setter
    @Getter
    int bossPhase = 1;
    globalClass global = new globalClass();
    double coreAngle = 0;
    final double coreRadius = 4.75;
    int tickCooldown = 0;
    int maxHealth = 1500;
    float movementSpeed = 0.375F;
    double mobScale = 0.95D;

    public quantumCore(net.minecraft.world.entity.EntityType<? extends Creeper> entityType, Level level) {
        super(entityType, level);

        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#70ba6d") + "Quantum Core"));
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(movementSpeed);
        this.getAttribute(Attributes.SCALE).setBaseValue(mobScale);
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
        this.setHealth(this.getMaxHealth());

        global.initBossBarTask(this, "Qᴜᴀɴᴛᴜᴍ Cᴏʀᴇ", BarColor.GREEN,"#5f940c");
        registerGoals();
    }

    public void shootQuantumProjectile(Player target) {
        org.bukkit.entity.Player bukkitTarget = (org.bukkit.entity.Player) target.getBukkitLivingEntity();
        LivingEntity shooter = this.getBukkitLivingEntity();
        Location spawnLoc = shooter.getEyeLocation();
        Vector direction = target.getBukkitEntity().getEyeLocation().subtract(spawnLoc).toVector().normalize();

        Snowball projectile = (Snowball) spawnLoc.getWorld().spawnEntity(spawnLoc, org.bukkit.entity.EntityType.SNOWBALL);

        for(org.bukkit.entity.Player p : Bukkit.getOnlinePlayers()) p.hideEntity(sleepyChronicles.getter(), projectile);

        projectile.setShooter(shooter);
        bukkitTarget.playSound(bukkitTarget.getLocation(), Sound.ITEM_CROSSBOW_SHOOT,0.85F,1.75F);
        bukkitTarget.playSound(bukkitTarget.getLocation(), Sound.ENTITY_ENDER_EYE_DEATH,0.5F,1.5F);

        projectile.getScoreboardTags().add("quantumProjectile");

        projectile.setVelocity(direction.multiply(1.6));

        doQuantumProjectileTask(projectile);
    }

    public void doQuantumProjectileTask(Projectile projectile) {
        ParticleManager particleManager = new ParticleManager(projectile.getWorld());

        new BukkitRunnable() {
            @Override
            public void run() {
                Location pLoc = projectile.getLocation();
                particleManager.spawnParticle(pLoc, Particle.EXPLOSION,null,
                        1,0,0,0,1.0);

                if(projectile.isDead() || projectile == null) this.cancel();

            }
        }.runTaskTimer(sleepyChronicles.getter(), 0, 2);

    }

    public void initSecondPhase(quantumCore entity){
        if(getBossPhase() == 2) return;

        this.setBossPhase(2);

        entity.goalSelector.getAvailableGoals().clear();

        entity.goalSelector.addGoal(0, new quantumCoreEnhancedProjectileGoal(entity));
        entity.goalSelector.addGoal(1, new quantumCoreMinionGoal(entity));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.getAvailableGoals().clear();
        this.targetSelector.getAvailableGoals().clear();

        this.goalSelector.addGoal(0,new quantumCoreProjectileGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, net.minecraft.world.entity.player.Player.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity bukkitEntity = this.getBukkitLivingEntity();

        if(this.isAlive()) {
            if(tickCooldown <= 0) {

                Location bossLoc = getOwner().getBukkitEntity().getLocation();
                coreAngle += 0.1;
                double x = bossLoc.getX() + (coreRadius * Math.cos(coreAngle));
                double y = bossLoc.getY() + 2.25;
                double z = bossLoc.getZ() + (coreRadius * Math.sin(coreAngle));
                Location newLoc = new Location(bossLoc.getWorld(), x, y, z);
                bukkitEntity.teleport(newLoc);

                // Change speed and aspect depending on the core's phase.
                if(this.getBossPhase() == 2) {
                    tickCooldown = 0;
                    bukkitEntity.setGlowing(true);
                    getCoreTeam().addEntity(bukkitEntity);
                    this.setPowered(true);
                } else tickCooldown = 2;

            } else tickCooldown--;

        } else getOwner().kill((ServerLevel) this.level());
    }

    @Override
    public Mob getEntity() {
        return this;
    }

    private Team getCoreTeam(){
        Scoreboard manager = Bukkit.getScoreboardManager().getMainScoreboard();
        Team greenTeam = manager.getTeam("greenTeam");

        if(greenTeam != null) return greenTeam;

        Team team = manager.registerNewTeam("greenTeam");
        team.setColor(org.bukkit.ChatColor.GREEN);

        return team;
    }
}
