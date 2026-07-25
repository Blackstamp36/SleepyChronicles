//package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.creeper.quantumCorePhase1;
//
//import lombok.Getter;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.ai.goal.Goal;
//import net.minecraft.world.level.Level;
//import org.blackstamp.sleepychronicles.SleepyChronicles;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.armor_stand.quantumBullet;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumCore;
//import org.bukkit.Sound;
//import org.bukkit.scheduler.BukkitRunnable;
//
//public class qcQuantumBulletsGoal extends Goal {
//
//    private final quantumCore entity;
//    private final int projectileDamage;
//    private final int tickCooldown;
//    @Getter
//    private final int projectileCount;
//
//    public qcQuantumBulletsGoal(quantumCore entity, int projectileDamage,
//                                int projectileCount, int tickCooldown) {
//        this.entity = entity;
//        this.projectileDamage = projectileDamage;
//        this.projectileCount = projectileCount;
//        this.tickCooldown = tickCooldown;
//    }
//
//    @Override
//    public boolean canUse() {
//        net.minecraft.world.entity.LivingEntity target = entity.getTarget();
//
//        return target != null
//                && entity.currentAttack.equals(quantumCore.bossAttacks.QUANTUM_BULLETS)
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
//        if(entity.getBossPhase() == 1) entity.currentAttack = quantumCore.bossAttacks.QUANTUM_BULLETS;
//        else entity.currentAttack = quantumCore.bossAttacks.MINIONS_SPELL;
//    }
//
//    @Override
//    public void tick(){
//        LivingEntity target = entity.getTarget();
//        if(target == null) return;
//
//        fireQuantumBullets(entity);
//    }
//
//    private void fireQuantumBullets(quantumCore entity){
//        org.bukkit.entity.Player bukkitT = (org.bukkit.entity.Player) entity.getTarget().getBukkitLivingEntity();
//        bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS,0.5F,1.25F);
//
//        Level nmsLevel = entity.level();
//
//        new BukkitRunnable() {
//            int tickCount = 0;
//            int projectilesShot = 0;
//
//            @Override
//            public void run() {
//                tickCount++;
//
//                if(projectilesShot >= getProjectileCount() || tickCount >= 120) this.cancel();
//
//                if(tickCount % 5 == 0) {
//                    projectilesShot++;
//
//                    if(entity.getTarget() == null){
//                        this.cancel();
//                        return;
//                    }
//
//                    quantumBullet projectile = new quantumBullet(EntityType.ARMOR_STAND, nmsLevel,
//                            projectileDamage, 20 + (int) (tickCount * 0.5), entity.getTarget(), entity);
//
//                    projectile.setPos(entity.position());
//                    nmsLevel.addFreshEntity(projectile);
//
//                    bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_BLAZE_SHOOT,0.15F,1.75F);
//                }
//            }
//        }.runTaskTimer(SleepyChronicles.getInstance(), 0, 1);
//
//    }
//
//
//}
