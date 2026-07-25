//package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.skeleton.ptPhase1;
//
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.ai.goal.Goal;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.GameType;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.skeleton.planterrorBoss;
//import org.blackstamp.sleepychronicles.api.particle.ParticleManager;
//import org.bukkit.Particle;
//import org.bukkit.Sound;
//import org.bukkit.potion.PotionEffect;
//import org.bukkit.potion.PotionEffectType;
//
//public class ptAmbushGoal extends Goal {
//    private final planterrorBoss entity;
//    private final int tickCooldown;
//    private final double maxDistance;
//
//    public ptAmbushGoal(planterrorBoss entity, double maxDistance,
//                        int tickCooldown){
//        this.entity = entity;
//        this.tickCooldown = tickCooldown;
//        this.maxDistance = maxDistance;
//    }
//
//    @Override
//    public boolean canUse() {
//        LivingEntity target = entity.getTarget();
//        if(target == null) return false;
//
//        boolean ambushRequirements = !entity.hasLineOfSight(target)
//                || entity.distanceTo(target) >= maxDistance;
//
//        return target instanceof Player
//                && ((Player) target).gameMode() == GameType.SURVIVAL
//                && ambushRequirements;
//    }
//
//    @Override
//    public boolean canContinueToUse() {
//        return canUse();
//    }
//
//    @Override
//    public boolean isInterruptable() {
//        return false;
//    }
//
//    @Override
//    public void start() {
//        entity.increaseTickCooldown(tickCooldown);
//    }
//
//    @Override
//    public void tick() {
//        LivingEntity target = entity.getTarget();
//        if (target == null) return;
//
//        executeTeleport();
//    }
//
//    private void executeTeleport(){
//        org.bukkit.entity.LivingEntity bukkitE = entity.getBukkitLivingEntity();
//        ParticleManager pM = new ParticleManager(bukkitE.getWorld());
//        LivingEntity target = entity.getTarget();
//        if (target == null) return;
//
//        org.bukkit.entity.LivingEntity bukkitT = target.getBukkitLivingEntity();
//
//        entity.teleportTo(target.getX(), target.getY(), target.getZ());
//        pM.particle(bukkitE.getLocation(), Particle.PORTAL, null,
//                25,0.05,0.05,0.05,1.0);
//        bukkitT.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,20,0,false,false));
//        if(bukkitT instanceof org.bukkit.entity.Player p)
//            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT,0.85F,0.25F);
//    }
//}
