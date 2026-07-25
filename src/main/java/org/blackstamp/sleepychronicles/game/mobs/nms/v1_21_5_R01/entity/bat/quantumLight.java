//package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.bat;
//
//import net.minecraft.world.effect.MobEffectInstance;
//import net.minecraft.world.effect.MobEffects;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.ai.attributes.Attributes;
//import net.minecraft.world.entity.ambient.Bat;
//import net.minecraft.world.level.Level;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.bossMob;
//import org.blackstamp.sleepychronicles.api.mobs.projectile.CustomProjectile;
//import org.blackstamp.sleepychronicles.api.particle.ParticleManager;
//import org.bukkit.Location;
//import org.bukkit.Particle;
//import org.bukkit.Sound;
//import org.bukkit.entity.Player;
//import org.bukkit.util.Vector;
//
//public class quantumLight extends Bat implements CustomProjectile {
//    private org.bukkit.entity.LivingEntity bukkitE = this.getBukkitLivingEntity();
//    private final Location targetPrevLoc;
//    private LivingEntity caster;
//    private int lifetimeTicks;
//    private int tickCount;
//    private final int projectileDamage = 60;
//    private final int particleCount = 4;
//    private final double blocksPerTick = 0.5;
//
//    public quantumLight(EntityType<? extends Bat> entityType, Level level,
//                        int lifetimeTicks, LivingEntity caster,
//                        LivingEntity target) {
//        super(entityType, level);
//        this.lifetimeTicks = lifetimeTicks;
//        this.caster = caster;
//        this.targetPrevLoc = target.getBukkitLivingEntity().getLocation();
//
//        registerAttributes();
//    }
//
//    public void registerAttributes(){
//        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1);
//        this.setHealth(this.getMaxHealth());
//
//        this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, -1));
//        this.setSilent(true);
//    }
//
//    @Override
//    public void tick() {
//        super.tick();
//        Location projectileLoc = new Location(bukkitE.getWorld(), bukkitE.getX(), bukkitE.getY() + 1.25, bukkitE.getZ());
//        tickCount++;
//
//        if(tickCount % 3 == 0) {
//            ParticleManager pM = new ParticleManager(bukkitE.getWorld());
//            pM.particle(projectileLoc, Particle.GUST, null,
//                    1, 0.0, 0.0, 0.0, 0.5);
//            pM.particle(projectileLoc, Particle.ELECTRIC_SPARK, null,
//                    particleCount, 0.25, 0.5, 0.25, 0.0);
//        }
//
//        Vector toTarget = targetPrevLoc.toVector().subtract(projectileLoc.toVector()).normalize();
//        Vector currentVel = bukkitE.getVelocity();
//        Vector newVel = currentVel.add(toTarget.multiply(0.6)).normalize().multiply(blocksPerTick);
//
//        if(newVel.length() < 0.001) return;
//
//        bukkitE.setVelocity(newVel);
//
//        if(!cM.getPlayerCollisions(this).isEmpty() || tickCount >= lifetimeTicks || !this.isAlive()) {
//            handleQLImpact();
//            this.discard();
//        }
//    }
//
//    private void handleQLImpact(){
//        ParticleManager pM = new ParticleManager(bukkitE.getWorld());
//        Location projectileLoc = new Location(bukkitE.getWorld(), bukkitE.getX(), bukkitE.getY() + 1.25, bukkitE.getZ());
//        pM.particle(projectileLoc, Particle.EXPLOSION_EMITTER, null,
//                1,0.0,0.0,0.0,0.5);
//
//        for (org.bukkit.entity.Entity n : this.getBukkitLivingEntity().getLocation().getNearbyEntities(6, 1d, 6)) {
//            if (!(n instanceof org.bukkit.entity.LivingEntity bukkitDE)) continue;
//            if (n instanceof bossMob) continue;
//
//            bukkitDE.damage(projectileDamage, caster.getBukkitLivingEntity());
//
//            if (n instanceof Player p) {
//                p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.75F, 1.25F);
//                p.playSound(p.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 0.25F, 0.75F);
//            }
//        }
//
//    }
//
//    @Override
//    public void handleImpact(LivingEntity damagedEntity) {
//    }
//}
