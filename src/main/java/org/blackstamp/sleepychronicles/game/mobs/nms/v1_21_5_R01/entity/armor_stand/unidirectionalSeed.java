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

public class unidirectionalSeed extends ArmorStand implements customProjectile {
    private org.bukkit.entity.LivingEntity bukkitE = this.getBukkitLivingEntity();
    private LivingEntity caster;
    private int lifetimeTicks;
    private int tickCount;
    private final int projectileDamage;
    private final int particleCount = 4;
    private final double blocksPerTick = 0.6;

    private final Vec3 direction;

    public unidirectionalSeed(EntityType<? extends ArmorStand> entityType, Level level,
                              int projectileDamage, int lifetimeTicks, LivingEntity caster, double degrees) {
        super(entityType, level);
        double radians = Math.toRadians(degrees);
        this.direction = new Vec3(Math.cos(radians), 0, Math.sin(radians));
        this.lifetimeTicks = lifetimeTicks;
        this.caster = caster;
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
        Location projectileLoc = new Location(bukkitE.getWorld(), bukkitE.getX(), bukkitE.getY() + 1.25, bukkitE.getZ());

        pM.spawnParticle(projectileLoc, Particle.EXPLOSION, null,
                1,0.0,0.0,0.0,0.25);
        pM.spawnParticle(projectileLoc, Particle.CHERRY_LEAVES, null,
                particleCount,0.25,0.5,0.25,0.25);
        pM.spawnParticle(projectileLoc, Particle.ENCHANTED_HIT, null,
                particleCount * 2,0.25,0.5,0.25,0.25);

        if (tickCount >= lifetimeTicks) {
            this.discard();
            return;
        }

        Vec3 movement = new Vec3(direction.x() * blocksPerTick, 0, direction.z() * blocksPerTick);
        setPos(position().add(movement));

        if(!cM.getPlayerCollisions(this).isEmpty())
            for (Entity e : cM.getPlayerCollisions(this)) handleProjectileImpact((LivingEntity) e);
    }

    public void handleProjectileImpact(LivingEntity damagedEntity){
        if(damagedEntity == null) return;

        org.bukkit.entity.LivingEntity bukkitDE = damagedEntity.getBukkitLivingEntity();

        bukkitDE.damage(projectileDamage, caster.getBukkitLivingEntity());
        bukkitDE.addPotionEffect(new PotionEffect(PotionEffectType.POISON,100,1));

        if(bukkitDE instanceof org.bukkit.entity.Player p) {
            p.playSound(p.getLocation(), Sound.ENTITY_ALLAY_ITEM_TAKEN, 0.75F, 1.25F);
            p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.75F, 0.75F);
            p.playSound(p.getLocation(), Sound.ENTITY_ILLUSIONER_HURT, 0.75F, 0.75F);
        }

        this.discard();
    }

}
