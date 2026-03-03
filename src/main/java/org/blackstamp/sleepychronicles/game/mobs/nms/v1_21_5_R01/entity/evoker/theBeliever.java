package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.evoker;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.bat.atomicDoom;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.bossMob;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.evoker.tbPhase1.tbFireballsGoal;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.evoker.tbPhase1.tbIceMistGoal;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.evoker.tbPhase1.tbLightningOrbGoal;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.evoker.tbPhase2.tbQuantumLightsGoal;
import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
import org.blackstamp.sleepychronicles.api.particle.ParticleManager;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.bukkit.entity.LivingEntity;

import java.util.concurrent.ThreadLocalRandom;

public class theBeliever extends Evoker implements bossMob {
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
    String bossName = "Tʜᴇ Bᴇʟɪᴇᴠᴇʀ";

    public enum bossAttacks{
        FIREBALLS,
        LIGHTNING_ORB,
        ICE_MIST,
        QUANTUM_LIGHTS
    }

    public theBeliever.bossAttacks currentAttack = bossAttacks.FIREBALLS;

    GlobalClass global = new GlobalClass();
    private final int maxHealth = 1250;
    private final double mobScale = 1.1D;
    private final int projectileDamage = 28;
    private final int atomicDoomChance = 35;

    private final String bossThemeKey = "sleepy.boss.the_believer.phase_1";
    private final int bossThemeDuration = 79;

    public theBeliever(EntityType<? extends Evoker> entityType, Level level) {
        super(entityType, level);

        registerGoals();
        registerAttributes();
       }

    public void registerGoals() {
        this.goalSelector.getAvailableGoals().clear();

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new tbFireballsGoal(this,
                projectileDamage,40));
        this.goalSelector.addGoal(2, new tbLightningOrbGoal(this,
                60));
        this.goalSelector.addGoal(3, new tbIceMistGoal(this,
                projectileDamage,40));
        this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this,
                Player.class, 5.0F, 0.45, 1.0F));

        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    private void registerAttributes(){
        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#447389") + "The Believer"));

        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
        this.getAttribute(Attributes.SCALE).setBaseValue(mobScale);
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
        this.setHealth(this.getMaxHealth());
        this.setSilent(true);

        global.initBossBarTask(this, bossName, BarColor.BLUE, "#447389");
    }

    @Override
    public void tick(){
        super.tick();

        decrementTickCooldown();
    }

    public void initSecondPhase(bossMob entity){
        if(entity.getBossPhase() == 2) return;
        Mob mob = entity.getEntity();

        entity.setBossPhase(2);

        mob.goalSelector.addGoal(4, new tbQuantumLightsGoal(this, 80));

        LivingEntity bukkitE = mob.getBukkitLivingEntity();
        Location l = bukkitE.getLocation();
        ParticleManager pM = new ParticleManager(bukkitE.getWorld());

        pM.particle(l, Particle.ANGRY_VILLAGER,null,
                15,0.25,0.5,0.25,0.0);
    }

    public void triggerAtomicDoom(net.minecraft.world.entity.LivingEntity target){
        if(target == null) return;
        if(!(target.getBukkitLivingEntity() instanceof org.bukkit.entity.Player p)) return;
        if(!(ThreadLocalRandom.current().nextInt(1,101) <= atomicDoomChance)) return;

        Vec3 lookVec = new Vec3(target.getX(), target.getY(), target.getZ());

        Level nmsLevel = this.level();
        final Vec3 startPos = new Vec3(this.getX(), this.getY(), this.getZ());

        this.getLookControl().setLookAt(lookVec);

        atomicDoom projectile = new atomicDoom(EntityType.BAT, nmsLevel,
               30, this, target);

        projectile.setPos(startPos);
        nmsLevel.addFreshEntity(projectile);

        p.playSound(p.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 0.85F,1.5F);
        p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.85F,0.75F);
        p.playSound(p.getLocation(), Sound.ITEM_BUCKET_FILL_AXOLOTL, 0.45F,1.25F);
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