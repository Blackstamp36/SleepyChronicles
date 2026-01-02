package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.armor_stand;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.customProjectile;
import org.blackstamp.sleepychronicles.global.utils.manager.ParticleManager;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class homingProjectile extends ArmorStand implements customProjectile {
    private org.bukkit.entity.LivingEntity bukkitE = this.getBukkitLivingEntity();
    private LivingEntity target;
    Location targetPrevLoc;
    private LivingEntity shooter;
    private int lifetimeTicks;
    private int delayTicks;
    private boolean isHoming = false;
    private int tickCount;
    private final int projectileDamage;
    private final int particleCount = 4;

    public homingProjectile(EntityType<? extends ArmorStand> entityType, Level level,
                            int projectileDamage, int lifetimeTicks, int delayTicks,
                            LivingEntity target, LivingEntity shooter) {
        super(entityType, level);
        this.target = target;
        this.lifetimeTicks = lifetimeTicks;
        this.delayTicks = delayTicks;
        this.shooter = shooter;
        this.targetPrevLoc = target.getBukkitLivingEntity().getLocation();
        this.projectileDamage = projectileDamage;

        registerAttributes();
    }

    public void registerAttributes(){
        this.setInvisible(true);
        this.setInvulnerable(true);
        this.setSilent(true);
    }

    @Override
    public void tick() {
        super.tick();
        tickCount++;

        ParticleManager pM = new ParticleManager(bukkitE.getWorld());
        Location projectileLoc = bukkitE.getLocation();

        pM.spawnParticle(projectileLoc, Particle.SQUID_INK, null,
                particleCount,0.05,0.35,0.05,0.25);
        pM.spawnParticle(projectileLoc, Particle.SWEEP_ATTACK, null,
                particleCount,0.05,0.35,0.05,0.5);

        if (tickCount >= lifetimeTicks) {
            pM.spawnParticle(projectileLoc, Particle.END_ROD, null,
                    particleCount * 3,0.05,0.35,0.05,0.25);
            this.discard();
            return;
        }

        if(tickCount == delayTicks) isHoming = true;

        if(isHoming) {
                showHomingParticles(pM, projectileLoc);
                Vec3 direction = target.position().subtract(position()).normalize();
                setDeltaMovement(direction.scale(0.5));

        } else {
            Vector toTarget = targetPrevLoc.toVector().subtract(projectileLoc.toVector()).normalize();
            Vector currentVel = bukkitE.getVelocity();
            Vector newVel = currentVel.add(toTarget.multiply(0.2)).normalize().multiply(0.6);

            if (newVel.length() < 0.001) return;

            bukkitE.setVelocity(newVel);

        }

        if(!cM.getPlayerCollisions(this).isEmpty())
            for (Entity e : cM.getPlayerCollisions(this)) handleProjectileImpact((LivingEntity) e);

    }

    public void handleProjectileImpact(LivingEntity damagedEntity){
        if(damagedEntity == null) return;

        org.bukkit.entity.LivingEntity bukkitDE = damagedEntity.getBukkitLivingEntity();

        bukkitDE.damage(projectileDamage, shooter.getBukkitLivingEntity());
        bukkitDE.addPotionEffect(new PotionEffect(PotionEffectType.POISON,60,2));
        bukkitDE.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,60,0));

        if(bukkitDE instanceof org.bukkit.entity.Player p) {
            p.playSound(p.getLocation(), Sound.ENTITY_ALLAY_HURT, 0.75F, 1.25F);
            p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 0.75F, 0.75F);
            p.playSound(p.getLocation(), Sound.ENTITY_ILLUSIONER_HURT, 0.75F, 0.75F);
        }

        this.discard();
    }

    private void showHomingParticles(ParticleManager pM, Location l){
        pM.spawnParticle(l, Particle.WITCH,null,
                particleCount * 2,0.25,0.25,0.25,1.0);
    }

}
