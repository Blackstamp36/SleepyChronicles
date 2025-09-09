package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.wither_boss;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.bossMob;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.llama.aggresiveLlama;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.phantom.seekerPhantom;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.wither_boss.mechanicalLungeGoal;
import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.color.ChatColor;
import org.blackstamp.sleepyChronicles.util.nms.NMSEntity;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.bukkit.entity.Wither;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@NMSEntity
public class mechanicalEye extends WitherBoss implements bossMob {
    private int tickCount = 0;

    @Setter
    @Getter
    private int bossPhase;

    public mechanicalEye(EntityType<? extends WitherBoss> entityType, Level level) {
        super(entityType, level);

        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#b34d4d") + "Mechanical Eye"));
        this.addTag("mechanicalEye");
        this.addTag("boss");
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(250);
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
        this.setHealth(this.getMaxHealth());
        this.setSilent(true);
        this.setAggressive(false);

        registerGoals();
        modifyWitherBossBar(this);
    }

    @Override
    public void tick() {
        super.tick();
        tickCount++;

        if (tickCount >= 60) {
            tickCount = 0;
            doMechanicalEyeTick(this);

        }
    }

    @Override
    public void registerGoals(){
        this.goalSelector.getAvailableGoals().clear();
        this.targetSelector.getAvailableGoals().clear();

        this.goalSelector.addGoal(1, new mechanicalLungeGoal(this, 3.6));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomFlyingGoal(this, 1.0));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

        private void doMechanicalEyeTick(Mob entity){
            int chance = ThreadLocalRandom.current().nextInt(0, 1000);

            if (entity.isAlive() && entity.getBukkitEntity().getLocation().getNearbyPlayers(35).isEmpty()) {
                entity.remove(RemovalReason.DISCARDED);
                return;
            }

            if (chance <= 49) {
                int count = ThreadLocalRandom.current().nextInt(1, 4);

                for (int i = 0; i < count; i++) {
                    Level nmsLevel = this.level();
                    seekerPhantom seeker = new seekerPhantom(EntityType.PHANTOM, nmsLevel);
                    nmsLevel.addFreshEntity(seeker);
                }
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

    @Override
    public Mob getEntity() {
        return this;
    }
}