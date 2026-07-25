//package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.zombie;
//
//import lombok.Getter;
//import lombok.Setter;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.Mob;
//import net.minecraft.world.entity.ai.attributes.Attributes;
//import net.minecraft.world.entity.ai.goal.FloatGoal;
//import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
//import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
//import net.minecraft.world.entity.monster.Monster;
//import net.minecraft.world.entity.monster.Zombie;
//import net.minecraft.world.level.Level;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.summonableMob;
//import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
//import org.bukkit.craftbukkit.util.CraftChatMessage;
//
//import java.util.UUID;
//
//public class stardustGolem extends Zombie implements summonableMob {
//    @Getter
//    @Setter
//    private UUID summonerUUID;
//    private int tickCount = 0;
//
//    public stardustGolem(EntityType<? extends Zombie> type, Level world) {
//        super(type, world);
//
//        this.setShouldBurnInDay(false);
//        this.setSilent(true);
//
//        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#64c7e8") + "Stardust Golem"));
//        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(20);
//        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.375);
//        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10.0);
//        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5);
//        this.getAttribute(Attributes.SCALE).setBaseValue(1.25);
//
//        registerGoals();
//    }
//
//    @Override
//    public void registerGoals(){ // GOALS DON'T SEEM TO INTERFERE.
//        this.goalSelector.getAvailableGoals().clear();
//        this.targetSelector.getAvailableGoals().clear();
//
//        this.goalSelector.addGoal(1, new FloatGoal(this));
//        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
//
//        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Monster.class, 10, true, false, null));
//    }
//
//    @Override
//    public void tick() {
//        super.tick();
//        tickCount++;
//
//        if (tickCount >= 300) killSummon(this);
//    }
//
//    @Override
//    public boolean isSummonable() {
//        return false;
//    }
//
//    @Override
//    public Mob getEntity() {
//        return this;
//    }
//
//    private void checkMobInfo(stardustGolem entity){
//        System.out.println("AI: " + entity.getBukkitLivingEntity().hasAI());
//        System.out.println("Persistent: " + entity.getBukkitLivingEntity().isPersistent());
//        System.out.println("Invulnerable: " + entity.getBukkitLivingEntity().isInvulnerable());
//        System.out.println("Valid: " + entity.getBukkitLivingEntity().isValid());
//    }
//}
//
