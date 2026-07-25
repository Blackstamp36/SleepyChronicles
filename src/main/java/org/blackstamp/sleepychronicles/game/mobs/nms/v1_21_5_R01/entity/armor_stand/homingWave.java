//package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.armor_stand;
//
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.decoration.ArmorStand;
//import net.minecraft.world.level.Level;
//import org.blackstamp.sleepychronicles.api.mobs.projectile.CustomProjectile;
//import org.blackstamp.sleepychronicles.global.utils.manager.CollisionManager;
//import org.blackstamp.sleepychronicles.api.particle.ParticleManager;
//import org.bukkit.Location;
//import org.bukkit.Material;
//import org.bukkit.Particle;
//import org.bukkit.Sound;
//import org.bukkit.entity.Player;
//import org.bukkit.potion.PotionEffect;
//import org.bukkit.potion.PotionEffectType;
//import org.bukkit.util.Vector;
//
//public class homingWave extends ArmorStand implements CustomProjectile { // todo: make earthquake goal for quantumBeast!
//    CollisionManager cM = new CollisionManager();
//    private org.bukkit.entity.LivingEntity bukkitE = this.getBukkitLivingEntity();
//    Location targetPrevLoc;
//    private LivingEntity shooter;
//    private int lifetimeTicks;
//    private int tickCount;
//    private final int projectileDamage;
//    private final int particleCount = 15;
//
//    public homingWave(EntityType<? extends ArmorStand> entityType, Level level,
//                      int projectileDamage, int lifetimeTicks, LivingEntity target, LivingEntity shooter) {
//        super(entityType, level);
//        this.lifetimeTicks = lifetimeTicks;
//        this.shooter = shooter;
//        this.targetPrevLoc = target.getBukkitLivingEntity().getLocation();
//        this.projectileDamage = projectileDamage;
//
//        registerAttributes();
//    }
//
//    public void registerAttributes(){
//        this.setInvisible(true);
//        this.setInvulnerable(true);
//        this.setSilent(true);
//        this.setSmall(true);
//    }
//
//    @Override
//    public void tick() {
//        super.tick();
//        tickCount++;
//
//        ParticleManager pM = new ParticleManager(bukkitE.getWorld());
//        Location projectileLoc = bukkitE.getLocation();
//
//        pM.particle(projectileLoc, Particle.BLOCK, Material.DIRT.createBlockData(),
//                particleCount,0.25,0.5,0.25,0.25);
//        pM.particle(projectileLoc, Particle.EXPLOSION, null,
//                1,0.25,0.5,0.25,0.25);
//
//        if(tickCount % 10 == 0) {
//            for (Player p : projectileLoc.getNearbyPlayers(3, 3, 3))
//                p.playSound(projectileLoc, Sound.BLOCK_ROOTED_DIRT_BREAK, 0.75F, 0.75F);
//        }
//
//        if(!cM.getPlayerCollisions(this).isEmpty() || tickCount >= lifetimeTicks) {
//            pM.particle(projectileLoc, Particle.END_ROD, null,
//                    particleCount,0.05,0.35,0.05,0.25);
//            for (Entity e : cM.getPlayerCollisions(this)) handleImpact((LivingEntity) e);
//
//            this.discard();
//            return;
//        }
//
//        Vector toTarget = targetPrevLoc.toVector().subtract(projectileLoc.toVector()).normalize();
//        Vector currentVel = bukkitE.getVelocity();
//        Vector newVel = currentVel.add(toTarget.multiply(0.2)).normalize().multiply(0.95);
//
//        if (newVel.length() < 0.001) return;
//
//        bukkitE.setVelocity(newVel);
//        }
//
//    public void handleImpact(LivingEntity damagedEntity){
//        if(damagedEntity == null) return;
//
//        org.bukkit.entity.LivingEntity bukkitDE = damagedEntity.getBukkitLivingEntity();
//
//        bukkitDE.damage(projectileDamage, shooter.getBukkitLivingEntity());
//        bukkitDE.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,60,1));
//
//        if(bukkitDE instanceof org.bukkit.entity.Player p) {
//            p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_SMALL_FALL, 0.75F, 1.25F);
//            p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 0.75F, 0.75F);
//            p.playSound(p.getLocation(), Sound.ENTITY_ILLUSIONER_HURT, 0.75F, 0.75F);
//            p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.75F, 0.75F);
//        }
//
//    }
//
//}
