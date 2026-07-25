//package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.wither_boss.mEPhase1;
//
//import net.minecraft.world.entity.ai.goal.Goal;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.wither_boss.mechanicalEye;
//import org.blackstamp.sleepychronicles.SleepyChronicles;
//import org.blackstamp.sleepychronicles.api.particle.ParticleManager;
//import org.bukkit.Bukkit;
//import org.bukkit.Location;
//import org.bukkit.Particle;
//import org.bukkit.Sound;
//import org.bukkit.entity.Player;
//
//import java.util.EnumSet;
//
//public class meMechanicalLungeGoal extends Goal {
//
//    private final mechanicalEye entity;
//    private final int tickCooldown;
//    private final double lungeDamage = 36;
//
//    public meMechanicalLungeGoal(mechanicalEye entity, int tickCooldown) {
//        this.entity = entity;
//        this.tickCooldown = tickCooldown;
//        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
//    }
//
//    @Override
//    public boolean canUse() {
//        net.minecraft.world.entity.LivingEntity target = entity.getTarget();
//
//        return target != null
//                && entity.currentAttack.equals(mechanicalEye.bossAttacks.MECHANICAL_LUNGE)
//                && entity.getTickCooldown() <= 0;
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
//    public void stop(){
//        entity.currentAttack = mechanicalEye.bossAttacks.BIG_FIREBALL;
//    }
//
//    @Override
//    public void tick(){
//        initLunge();
//    }
//
//    private void initLunge(){
//        net.minecraft.world.entity.LivingEntity target = entity.getTarget();
//
//        if(target == null) return;
//        org.bukkit.entity.LivingEntity bukkitT = target.getBukkitLivingEntity();
//        entity.setNoAi(true);
//
//        if(bukkitT instanceof Player p) p.playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE,0.85F,1.75F);
//
//        entity.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), 1.25);
//
//        Bukkit.getScheduler().runTaskLater(SleepyChronicles.getInstance(), this::executeLungeDamage,20);
//    }
//
//    private void executeLungeDamage(){
//        entity.setNoAi(false);
//        Location l = entity.getBukkitLivingEntity().getLocation();
//        ParticleManager pM = new ParticleManager(l.getWorld());
//        pM.particle(l, Particle.SWEEP_ATTACK,null,
//                100,2.5,2.5,2.5,1.0);
//        pM.particle(l, Particle.EXPLOSION_EMITTER,null,
//                1,2.5,2.5,2.5,1.0);
//
//        for(Player p : l.getNearbyPlayers(5)){
//            p.damage(lungeDamage, entity.getBukkitLivingEntity());
//            p.playSound(p.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK,0.85F,0.75F);
//            pM.particle(p.getLocation(), Particle.WITCH,null,
//                    5,0.5,0.5,0.5,0.5);
//        }
//    }
//
//}