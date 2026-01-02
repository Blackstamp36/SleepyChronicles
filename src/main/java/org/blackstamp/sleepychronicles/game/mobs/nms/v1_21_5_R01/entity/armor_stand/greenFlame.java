package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.armor_stand;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.customProjectile;
import org.blackstamp.sleepychronicles.global.utils.manager.ParticleManager;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class greenFlame extends ArmorStand implements customProjectile {
    private org.bukkit.entity.LivingEntity bukkitE = this.getBukkitLivingEntity();
    Location targetPrevLoc;
    private LivingEntity shooter;
    private int lifetimeTicks;
    private int tickCount;
    private int projectileDamage;
    private final int particleCount = 4;

    public greenFlame(EntityType<? extends ArmorStand> entityType, Level level,
                      int projectileDamage, int lifetimeTicks, LivingEntity target, LivingEntity caster) {
        super(entityType, level);
        this.lifetimeTicks = lifetimeTicks;
        this.shooter = caster;
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

        pM.spawnParticle(projectileLoc, Particle.DUST_COLOR_TRANSITION, new Particle.DustTransition(
                Color.fromRGB(133, 15, 15),
                Color.fromRGB(60, 207, 23),
                1.25F
        ), particleCount * 2,0.05,0.35,0.05,0.5);

        pM.spawnParticle(projectileLoc, Particle.EXPLOSION, null,
                1,0.05,0.05,0.05,0.5);

        if (tickCount >= lifetimeTicks) {
            this.discard();
            return;
        }

        Vector toTarget = targetPrevLoc.toVector().subtract(projectileLoc.toVector()).normalize();
        Vector currentVel = bukkitE.getVelocity();
        Vector newVel = currentVel.add(toTarget.multiply(0.2)).normalize().multiply(0.6);

        if (newVel.length() < 0.001) return;

        bukkitE.setVelocity(newVel);

        if(!cM.getPlayerCollisions(this).isEmpty())
            for (Entity e : cM.getPlayerCollisions(this)) handleProjectileImpact((LivingEntity) e);

            }

    public void handleProjectileImpact(LivingEntity damagedEntity){
        if(damagedEntity == null) return;

        org.bukkit.entity.LivingEntity bukkitDE = damagedEntity.getBukkitLivingEntity();

        bukkitDE.damage(projectileDamage, shooter.getBukkitLivingEntity());
        bukkitDE.addPotionEffect(new PotionEffect(PotionEffectType.POISON,100,1));

        if(bukkitDE instanceof org.bukkit.entity.Player p) {
            p.playSound(p.getLocation(), Sound.ENTITY_ALLAY_ITEM_TAKEN, 0.75F, 1.25F);
            p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.75F, 0.75F);
            p.playSound(p.getLocation(), Sound.ENTITY_ILLUSIONER_HURT, 0.75F, 0.75F);
        }

        this.discard();
    }
}
