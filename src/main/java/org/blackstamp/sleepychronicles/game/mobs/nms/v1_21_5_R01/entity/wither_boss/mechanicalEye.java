//package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.wither_boss;
//
//import lombok.Getter;
//import lombok.Setter;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.Mob;
//import net.minecraft.world.entity.ai.attributes.Attributes;
//import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
//import net.minecraft.world.entity.boss.wither.WitherBoss;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Level;
//import org.blackstamp.sleepychronicles.global.GlobalClass;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.bossMob;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.phantom.seekerPhantom;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.wither_boss.mEPhase1.meBigFireballGoal;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.wither_boss.mEPhase1.meDistanceGoal;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.wither_boss.mEPhase1.meMechanicalLungeGoal;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.wither_boss.mEPhase1.meGreenFlamesGoal;
//import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
//import org.bukkit.boss.BarColor;
//import org.bukkit.craftbukkit.util.CraftChatMessage;
//
//import java.util.concurrent.ThreadLocalRandom;
//
//public class mechanicalEye extends WitherBoss implements bossMob {
//
//    @Setter
//    @Getter
//    private int bossPhase = 1;
//    @Getter
//    int tickCount = 0;
//    @Getter
//    @Setter
//    int tickCooldown = 0;
//
//    @Getter
//    @Setter
//    String bossName = "Mᴇᴄʜᴀɴɪᴄᴀʟ Eʏᴇ";
//
//    public enum bossAttacks{
//        GREEN_FLAMES,
//        MECHANICAL_LUNGE,
//        BIG_FIREBALL
//    }
//
//    public bossAttacks currentAttack = bossAttacks.GREEN_FLAMES;
//
//    GlobalClass global = new GlobalClass();
//    private final int maxHealth = 250;
//    private final double mobScale = 1.25D;
//    private int projectileDamage = 16;
//    private int projectileCount = 3;
//
//    private final String bossThemeKey = "sleepy.boss.mechanical_eye.phase_1";
//    private final int bossThemeDuration = 143;
//
//    public mechanicalEye(EntityType<? extends WitherBoss> entityType, Level level) {
//        super(entityType, level);
//
//        registerAttributes();
//        registerGoals();
//
//        global.initBossBarTask(this, bossName, BarColor.RED, "#b34d4d");
//    }
//
//    public void registerGoals() {
//        this.goalSelector.getAvailableGoals().clear();
//
//        this.goalSelector.addGoal(0, new meGreenFlamesGoal(this,
//                projectileDamage, projectileCount,60));
//        this.goalSelector.addGoal(1, new meMechanicalLungeGoal(this,20));
//        this.goalSelector.addGoal(2, new meBigFireballGoal(this,
//                projectileDamage * 2,60));
//        this.goalSelector.addGoal(3, new meDistanceGoal(this, 8.0,16.0));
//
//        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
//    }
//
//    private void registerAttributes(){
//        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#b34d4d") + "Mechanical Eye"));
//
//        bossEvent.visible = false;
//
//        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
//        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
//        this.getAttribute(Attributes.SCALE).setBaseValue(mobScale);
//        this.setHealth(this.getMaxHealth());
//
//        this.setSilent(true);
//    }
//
//    @Override
//    public void customServerAiStep(ServerLevel level) {
//        tickCount++;
//        decrementTickCooldown();
//
//        if (tickCount >= 60) {
//            tickCount = 0;
//            doMechanicalEyeTick();
//
//        }
//    }
//
//        private void doMechanicalEyeTick(){
//            int chance = ThreadLocalRandom.current().nextInt(0, 1000);
//
//            if (chance <= 49) {
//                int count = ThreadLocalRandom.current().nextInt(1, 5);
//
//                for (int i = 0; i < count; i++) {
//                    Level nmsLevel = this.level();
//                    seekerPhantom seeker = new seekerPhantom(EntityType.PHANTOM, nmsLevel);
//                    nmsLevel.addFreshEntity(seeker);
//                }
//            }
//        }
//
//    @Override
//    public void decrementTickCooldown() {
//        if(tickCooldown > 0) tickCooldown--;
//    }
//
//    @Override
//    public void increaseTickCooldown(int value) {
//        tickCooldown+= value;
//    }
//
//    @Override
//    public int getThemeDurationTicks() {
//        return bossThemeDuration * 20;
//    }
//
//    @Override
//    public String getBossTheme() {
//        return bossThemeKey;
//    }
//
//    @Override
//    public Mob getEntity() {
//        return this;
//    }
//}