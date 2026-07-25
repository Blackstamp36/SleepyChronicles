//package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.armor_stand;
//
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.ai.attributes.Attributes;
//import net.minecraft.world.entity.decoration.ArmorStand;
//import net.minecraft.world.level.Level;
//import org.blackstamp.sleepychronicles.api.mobs.projectile.CustomProjectile;
//import org.blackstamp.sleepychronicles.api.particle.ParticleManager;
//import org.bukkit.Color;
//import org.bukkit.Location;
//import org.bukkit.Particle;
//import org.bukkit.Sound;
//import org.bukkit.potion.PotionEffect;
//import org.bukkit.potion.PotionEffectType;
//import org.bukkit.util.Vector;
//
//public class darkLatchet extends ArmorStand implements CustomProjectile {
//    private org.bukkit.entity.LivingEntity bukkitE = this.getBukkitLivingEntity();
//    Location targetPrevLoc;
//    private LivingEntity shooter;
//    private int lifetimeTicks;
//    private int tickCount;
//    private final int projectileDamage;
//    private final int particleCount = 4;
//
//    public darkLatchet(EntityType<? extends ArmorStand> entityType, Level level,
//                       int projectileDamage, int lifetimeTicks, LivingEntity target, LivingEntity shooter) {
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
//        this.getAttribute(Attributes.SCALE).setBaseValue(1.25F);
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
//        pM.particle(projectileLoc, Particle.DUST_COLOR_TRANSITION, new Particle.DustTransition(
//                Color.fromRGB(0, 0, 0),
//                Color.fromRGB(94, 23, 161),
//                2.5F
//        ),
//                particleCount,0.05,0.35,0.05,0.5);
//
//        if (tickCount >= lifetimeTicks) {
//            this.discard();
//            return;
//        }
//
//            Vector toTarget = targetPrevLoc.toVector().subtract(projectileLoc.toVector()).normalize();
//            Vector currentVel = bukkitE.getVelocity();
//            Vector newVel = currentVel.add(toTarget.multiply(0.2)).normalize().multiply(0.4);
//
//            if (newVel.length() < 0.001) return;
//
//            bukkitE.setVelocity(newVel);
//
//        if(!cM.getPlayerCollisions(this).isEmpty())
//            for (Entity e : cM.getPlayerCollisions(this)) handleImpact((LivingEntity) e);
//
//    }
//
//    public void handleImpact(LivingEntity damagedEntity){
//        if(damagedEntity == null) return;
//
//        org.bukkit.entity.LivingEntity bukkitDE = damagedEntity.getBukkitLivingEntity();
//
//        bukkitDE.damage(projectileDamage, shooter.getBukkitLivingEntity());
//        bukkitDE.addPotionEffect(new PotionEffect(PotionEffectType.POISON,60,2));
//        bukkitDE.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,60,0));
//        bukkitDE.addPotionEffect(new PotionEffect(PotionEffectType.WIND_CHARGED,200,0));
//
//        if(damagedEntity instanceof org.bukkit.entity.Player p) {
//            p.playSound(p.getLocation(), Sound.ENTITY_ALLAY_HURT, 0.75F, 1.25F);
//            p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 0.75F, 0.75F);
//            p.playSound(p.getLocation(), Sound.ENTITY_ILLUSIONER_HURT, 0.75F, 0.75F);
//        }
//
//    }
//}
