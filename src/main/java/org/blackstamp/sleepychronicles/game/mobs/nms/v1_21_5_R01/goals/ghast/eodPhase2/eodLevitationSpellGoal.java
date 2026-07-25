//package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.ghast.eodPhase2;
//
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.ai.goal.Goal;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.phys.Vec3;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.armor_stand.darkLatchet;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.ghast.emperorOfDarkness;
//import org.blackstamp.sleepychronicles.SleepyChronicles;
//import org.bukkit.Sound;
//import org.bukkit.potion.PotionEffect;
//import org.bukkit.potion.PotionEffectType;
//import org.bukkit.scheduler.BukkitRunnable;
//
//import java.util.Random;
//
//public class eodLevitationSpellGoal extends Goal {
//
//    private final Random r = new Random();
//    private final emperorOfDarkness entity;
//    private final int latchetDistance;
//    private final int projectileDamage;
//    private final int tickCooldown;
//
//    public eodLevitationSpellGoal(emperorOfDarkness entity, int latchetDistance,
//                                  int projectileDamage, int tickCooldown) {
//        this.entity = entity;
//        this.latchetDistance = latchetDistance;
//        this.projectileDamage = projectileDamage;
//        this.tickCooldown = tickCooldown;
//    }
//
//    @Override
//    public boolean canUse() {
//        net.minecraft.world.entity.LivingEntity target = entity.getTarget();
//
//        return target != null
//                && entity.currentAttack.equals(emperorOfDarkness.phase2Attacks.LEVITATION_SPELL)
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
//        entity.currentAttack = emperorOfDarkness.phase2Attacks.SUPERNOVA;
//    }
//
//    @Override
//    public void tick(){
//        super.tick();
//
//        initLevitationSpell();
//    }
//
//    private void initLevitationSpell(){
//        if (!(entity.getTarget() instanceof Player target)) return;
//        org.bukkit.entity.Player bukkitT = (org.bukkit.entity.Player) target.getBukkitLivingEntity();
//        bukkitT.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,100,0, false,false));
//        bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS,0.5F,1.25F);
//
//        Level nmsLevel = entity.level();
//
//        new BukkitRunnable() {
//            int tickCount = 0;
//
//            @Override
//            public void run() {
//                tickCount++;
//
//                if(tickCount >= 100) this.cancel();
//
//                if(tickCount % 20 == 0) {
//                    darkLatchet projectile = new darkLatchet(EntityType.ARMOR_STAND, nmsLevel,
//                            projectileDamage,40 + (int) (tickCount * 0.5), target, entity);
//
//                    projectile.setPos(getLatchetNewLoc());
//                    nmsLevel.addFreshEntity(projectile);
//                    bukkitT.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,
//                            20,0, false,false));
//                    bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_WITHER_SPAWN,0.5F,1.25F);
//                    bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_GHAST_SHOOT, 0.85F,1.75F);
//                }
//            }
//        }.runTaskTimer(SleepyChronicles.getInstance(), 0, 1);
//
//    }
//
//    private Vec3 getLatchetNewLoc(){
//        net.minecraft.world.entity.LivingEntity target = entity.getTarget();
//        if(target == null) return null;
//
//        Vec3 startPos = new Vec3(target.getX(), target.getY(), target.getZ());
//
//        double angle = r.nextDouble() * 2 * Math.PI;
//
//        double x = startPos.x() + Math.cos(angle) * latchetDistance;
//        double y = startPos.y() + 3;
//        double z = startPos.z() + Math.sin(angle) * latchetDistance;
//
//        return new Vec3(x, y, z);
//    }
//}
