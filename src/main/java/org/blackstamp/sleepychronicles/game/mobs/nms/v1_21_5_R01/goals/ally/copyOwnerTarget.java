//package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.ally;
//
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.Mob;
//import net.minecraft.world.entity.ai.goal.Goal;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.summonableMob;
//import org.bukkit.Bukkit;
//import org.bukkit.craftbukkit.entity.CraftPlayer;
//import org.bukkit.entity.Player;
//import org.bukkit.event.entity.EntityTargetEvent;
//
//public class copyOwnerTarget extends Goal {
//
//    private final summonableMob mob; // Your custom ally mob
//    private final Mob entity;
//    private final double speedModifier;
//    private final boolean checkVisibility;
//    private final boolean checkCanNavigate;
//    private int seeTime;
//    private int cooldown = 0;
//
//    public copyOwnerTarget(summonableMob mob, double speed, boolean checkVisibility, boolean checkCanNavigate) {
//        this.mob = mob;
//        this.entity = mob.getEntity();
//        this.speedModifier = speed;
//        this.checkVisibility = checkVisibility;
//        this.checkCanNavigate = checkCanNavigate;
//    }
//
//    @Override
//    public boolean canUse() {
//        if (this.mob.getSummonerUUID() == null) {
//            return false;
//        }
//
//        Player summoner = Bukkit.getPlayer(mob.getSummonerUUID());
//        if (summoner == null || !summoner.isOnline()) return false;
//
//        if (cooldown > 0) {
//            cooldown--;
//            return false;
//        }
//
//        this.cooldown = 10;
//
//        LivingEntity summonersTarget = getSummonersTarget(summoner);
//        if (summonersTarget == null) return false;
//
//        return isValidTarget(summonersTarget, summoner);
//    }
//
//    @Override
//    public boolean canContinueToUse() {
//        LivingEntity target = mob.getEntity();
//        return target != null && target.isAlive();
//    }
//
//    @Override
//    public void start() {
//        seeTime = 0;
//        entity.setTarget(getSummonersTarget(Bukkit.getPlayer(mob.getSummonerUUID())), EntityTargetEvent.TargetReason.OWNER_ATTACKED_TARGET);
//
//        if(entity.getTarget() == null) return;
//
//        entity.getNavigation().moveTo(entity.getTarget(), this.speedModifier);
//    }
//
//    @Override
//    public void stop() {
//        entity.setTarget(null);
//    }
//
//    @Override
//    public void tick() {
//        LivingEntity target = entity.getTarget();
//        if (target != null) entity.getNavigation().moveTo(target, this.speedModifier);
//
//    }
//
//    private LivingEntity getSummonersTarget(Player summoner) {
//        CraftPlayer craftPlayer = (CraftPlayer) summoner;
//        LivingEntity nmsPlayer = craftPlayer.getHandle();
//
//        return nmsPlayer.getLastHurtMob();
//    }
//
//    private boolean isValidTarget(LivingEntity target, Player summoner) {
//        if (target == summoner || target instanceof summonableMob) return false;
//
//        return target.isAlive();
//    }
//}
//
