package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.bogged;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Bogged;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.bogged.breezeraPhase1;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.bogged.breezeraPhase2Ambush;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.bogged.breezeraPhase2Charge;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.bogged.breezeraPhase2RapidFire;
import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class breezeraBoss extends Bogged {
    @Getter
    @Setter
    private int phase = 1;

    public breezeraBoss(EntityType<? extends Bogged> entityType, Level level) {
        super(entityType, level);

        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#5f940c") + "Breezera"));
        this.addTag("breezeraBoss");
        this.addTag("boss");
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(750);
        this.getAttribute(Attributes.SCALE).setBaseValue(2.25);
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
        this.setHealth(this.getMaxHealth());
        this.setSilent(true);
        this.setShouldBurnInDay(false);

        this.getWeaponItem().asBukkitMirror().setType(Material.AIR);

        this.goalSelector.getAvailableGoals().clear();
        this.targetSelector.getAvailableGoals().clear();

        this.goalSelector.addGoal(1, new breezeraPhase1(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));

        initBreezeraTask(this);
    }

    public void shootSeed(Player target, boolean isSpiky) {
        org.bukkit.entity.Player bukkitTarget = (org.bukkit.entity.Player) target.getBukkitLivingEntity();
        LivingEntity boss = this.getBukkitLivingEntity();
        Location spawnLoc = boss.getEyeLocation();
        Vector direction = target.getBukkitEntity().getEyeLocation().subtract(spawnLoc).toVector().normalize();

        Snowball projectile = (Snowball) spawnLoc.getWorld().spawnEntity(spawnLoc, org.bukkit.entity.EntityType.SNOWBALL);

        projectile.setShooter(boss);
        bukkitTarget.playSound(bukkitTarget.getLocation(), Sound.ENTITY_SHULKER_SHOOT,1,1.5F);
        bukkitTarget.playSound(bukkitTarget.getLocation(), Sound.ITEM_HOE_TILL,0.5F,1.5F);

        if(!isSpiky) projectile.getScoreboardTags().add("greenSeed");
        else projectile.getScoreboardTags().add("spikySeed");

        projectile.setVelocity(direction.multiply(1.5));
    }

    public void startPhaseTwo() {
        if (this.phase == 2) return;
        this.phase = 2;

        LivingEntity bukkitBoss = this.getBukkitLivingEntity();

        this.setInvulnerable(true);
        bukkitBoss.getWorld().playSound(bukkitBoss.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1.25F, 0.75F);

        final Location particleLoc = bukkitBoss.getLocation();

        new BukkitRunnable() {
            int ticks = 0;
            @Override
            public void run() {
                ticks++;

                for (int i = 0; i < 20; i++) {
                    double angle = 2 * Math.PI * i / 20;
                    double x = Math.cos(angle) * 2;
                    double z = Math.sin(angle) * 2;
                    particleLoc.getWorld().spawnParticle(Particle.SMOKE,
                            particleLoc.getX() + x,
                            particleLoc.getY() + 1,
                            particleLoc.getZ() + z,
                            2, 0, 0, 0, 0.05);
                }

                if (ticks >= 20) { // 1s.
                    this.cancel();
                    completePhaseTwoTransform(particleLoc);
                }
            }
        }.runTaskTimer(sleepyChronicles.getter(), 0, 1);
    }

    private void completePhaseTwoTransform(Location loc) {
        loc.getWorld().spawnParticle(Particle.EXPLOSION_EMITTER, loc, 1);
        loc.getWorld().playSound(loc, Sound.ENTITY_RAVAGER_ROAR, 1.0F, 0.75F);

        loc.getWorld().getNearbyEntities(loc, 8, 8, 8).forEach(entity -> {
            if (entity instanceof org.bukkit.entity.Player p) {

                Vector direction = p.getLocation().toVector().subtract(loc.toVector()).normalize();
                p.setVelocity(direction.multiply(1.5).setY(0.7));
            }
        });

        this.goalSelector.getAvailableGoals().clear();
        this.targetSelector.getAvailableGoals().clear();

        this.goalSelector.addGoal(0, new breezeraPhase2Charge(this));
        this.goalSelector.addGoal(1, new breezeraPhase2RapidFire(this));
        this.goalSelector.addGoal(2, new breezeraPhase2Ambush(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));

        this.setInvulnerable(false);
    }

    private void initBreezeraTask(breezeraBoss breeze) {
        final BossBar finalBossBar = createBossbar();

        new BukkitRunnable() {
            @Override
            public void run() {
                LivingEntity bukkitBreeze = breeze.getBukkitLivingEntity();

                if (!breeze.isAlive() || !bukkitBreeze.isValid()) {
                    finalBossBar.removeAll();
                    this.cancel();
                    return;
                }

                double currentHealth = breeze.getHealth();
                double maxHealth = breeze.getMaxHealth();
                double progress = Math.clamp(currentHealth / maxHealth, 0.0, 1.0);

                if(breeze.getPhase() == 2) finalBossBar.setTitle(
                        "§8• §k|§f" + ChatColor.of("#5f940c") + " Bʀᴇᴇᴢᴇʀᴀ §c[\uD83D\uDD25] " + "§8§k|§f §8•");
                finalBossBar.setProgress(progress);

                Location witherLoc = bukkitBreeze.getLocation();
                Collection<org.bukkit.entity.Player> playersInRange = witherLoc.getNearbyPlayers(35);
                Set<org.bukkit.entity.Player> currentViewers = new HashSet<>(finalBossBar.getPlayers());

                for(org.bukkit.entity.Player viewer : currentViewers) {
                    if (!playersInRange.contains(viewer)) {
                        finalBossBar.removePlayer(viewer);
                    }
                }

                for(org.bukkit.entity.Player player : playersInRange) {
                    if (!currentViewers.contains(player)) {
                        finalBossBar.addPlayer(player);
                    }
                }

                if (playersInRange.isEmpty()) {
                    finalBossBar.removeAll();
                    breeze.remove(RemovalReason.DISCARDED);
                    this.cancel();
                }
            }
        }.runTaskTimer(sleepyChronicles.getter(), 0, 20);
    }

    public BossBar createBossbar() {
        return Bukkit.createBossBar(
                "§8• §k|§f" + ChatColor.of("#5f940c") + " Bʀᴇᴇᴢᴇʀᴀ " + "§8§k|§f §8•",
                BarColor.GREEN,
                BarStyle.SEGMENTED_12
        );
    }


    public static void spawnEntity(Location loc, int entities) {
        ServerLevel nmsLvl = ((CraftWorld) loc.getWorld()).getHandle();

        for (int i = 0; i < entities; i++) {
            breezeraBoss e = new breezeraBoss(EntityType.BOGGED, nmsLvl);
            e.setPos(loc.getX(), loc.getY(), loc.getZ());
            nmsLvl.addFreshEntity(e);
        }
    }

}