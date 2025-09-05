package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.wither_boss;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.phantom.seekerPhantom;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.wither_boss.mechanicalLungeGoal;
import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.bukkit.entity.Wither;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class mechanicalEye extends WitherBoss {
    Random r = new Random();

    public mechanicalEye(EntityType<? extends WitherBoss> entityType, Level level) {
        super(entityType, level);

        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#b34d4d") + "Mechanical Eye"));
        this.addTag("mechanicalEye");
        this.addTag("boss");
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(250);
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
        this.setHealth(250);
        this.setSilent(true);
        this.setAggressive(false);

        this.goalSelector.getAvailableGoals().clear();
        this.targetSelector.getAvailableGoals().clear();

        this.goalSelector.addGoal(1, new mechanicalLungeGoal(this, 3.6));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomFlyingGoal(this, 1.0));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));

        modifyWitherBossBar(this);
        initMechanicalEyeTask(this);
    }

    public static void spawnEntity(Location loc, int entities) {
        ServerLevel nmsLvl = ((CraftWorld) loc.getWorld()).getHandle();

        for (int i = 0; i < entities; i++) {
            mechanicalEye e = new mechanicalEye(EntityType.WITHER, nmsLvl);
            e.setPos(loc.getX(), loc.getY(), loc.getZ());
            nmsLvl.addFreshEntity(e);

            e.modifyWitherBossBar(e);
        }
    }

    public void modifyWitherBossBar(WitherBoss wither) {
        Wither witherBoss = (Wither) wither.getBukkitEntity();
        org.bukkit.boss.BossBar bossBar = witherBoss.getBossBar();

        if (bossBar != null) {
            bossBar.setColor(BarColor.RED);
            bossBar.setStyle(BarStyle.SEGMENTED_20);
            bossBar.setTitle("§8• §k|§f" + ChatColor.of("#b83d3d") + " Mᴇᴄʜᴀɴɪᴄᴀʟ Eʏᴇ " + "§8§k|§f §8•");
        }
    }

    private void initMechanicalEyeTask(WitherBoss wither){
        new BukkitRunnable() {
            @Override
            public void run() {
                int chance = r.nextInt(1001);
                if(chance <= 49){
                    seekerPhantom.spawnEntity(wither.getBukkitEntity().getLocation(), r.nextInt(1,5));
                }

                if (wither.isAlive() && wither.getBukkitEntity().getLocation().getNearbyPlayers(35).isEmpty()) {
                    wither.remove(RemovalReason.DISCARDED);
                    this.cancel();

                } else if(!wither.isAlive()){
                    this.cancel();
                }
            }
        }.runTaskTimer(sleepyChronicles.getter(), 0, 40);
    }
}