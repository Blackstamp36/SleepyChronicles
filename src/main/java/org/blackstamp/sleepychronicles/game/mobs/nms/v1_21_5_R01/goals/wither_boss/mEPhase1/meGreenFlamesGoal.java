//package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.wither_boss.mEPhase1;
//
//import lombok.Getter;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.ai.goal.Goal;
//import net.minecraft.world.level.Level;
//import org.blackstamp.sleepychronicles.SleepyChronicles;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.armor_stand.greenFlame;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.wither_boss.mechanicalEye;
//import org.bukkit.Sound;
//import org.bukkit.scheduler.BukkitRunnable;
//
//public class meGreenFlamesGoal extends Goal {
//
//    private final mechanicalEye entity;
//    private final int projectileDamage;
//    private final int tickCooldown;
//    @Getter
//    private final int projectileCount;
//
//    public meGreenFlamesGoal(mechanicalEye entity, int projectileDamage,
//                             int projectileCount, int tickCooldown) {
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
//                && entity.currentAttack.equals(mechanicalEye.bossAttacks.GREEN_FLAMES)
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
//        entity.currentAttack = mechanicalEye.bossAttacks.MECHANICAL_LUNGE;
//    }
//
//    @Override
//    public void tick(){
//        LivingEntity target = entity.getTarget();
//        if(target == null) return;
//
//        shootGreenFlames(entity);
//    }
//
//    private void shootGreenFlames(mechanicalEye entity){
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
//                    if(entity.getTarget() == null) this.cancel();
//
//                    greenFlame projectile = new greenFlame(EntityType.ARMOR_STAND, nmsLevel,
//                            projectileDamage, 40 + (int) (tickCount * 0.5), entity.getTarget(), entity);
//
//                    projectile.setPos(entity.position());
//                    nmsLevel.addFreshEntity(projectile);
//
//                    bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_WITHER_SPAWN,0.15F,1.75F);
//                }
//            }
//        }.runTaskTimer(SleepyChronicles.getInstance(), 0, 1);
//
//    }
//}
