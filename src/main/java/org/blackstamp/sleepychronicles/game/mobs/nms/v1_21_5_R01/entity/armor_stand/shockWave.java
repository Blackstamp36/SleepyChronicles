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
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class shockWave extends ArmorStand implements customProjectile {
    private org.bukkit.entity.LivingEntity bukkitE = this.getBukkitLivingEntity();
    private LivingEntity caster;
    private int lifetimeTicks;
    private int tickCount;
    private final int waveDamage;
    private final int particleCount = 4;
    private final double blocksPerTick = 0.6;

    private final Vec3 direction;

    public shockWave(EntityType<? extends ArmorStand> entityType, Level level,
                     int waveDamage, int lifetimeTicks, LivingEntity caster, double degrees) {
        super(entityType, level);
        double radians = Math.toRadians(degrees);
        this.direction = new Vec3(Math.cos(radians), 0, Math.sin(radians));
        this.lifetimeTicks = lifetimeTicks;
        this.caster = caster;
        this.waveDamage = waveDamage;

        registerAttributes();
    }

    public void registerAttributes(){
        this.setInvisible(true);
        this.setInvulnerable(true);
        this.setSilent(true);
        this.setSmall(true);
    }

    @Override
    public void tick() {
        super.tick();
        tickCount++;

        ParticleManager pM = new ParticleManager(bukkitE.getWorld());
        Location projectileLoc = bukkitE.getLocation();

        pM.spawnParticle(projectileLoc, Particle.BLOCK, Material.DIRT.createBlockData(),
                particleCount,0.05,0.35,0.05,0.25);
        pM.spawnParticle(projectileLoc, Particle.SWEEP_ATTACK, null,
                particleCount,0.05,0.35,0.05,0.75);

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
        org.bukkit.entity.LivingEntity bukkitDE = damagedEntity.getBukkitLivingEntity();

        bukkitDE.damage(waveDamage, caster.getBukkitLivingEntity());
        bukkitDE.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,60,0));

        if(bukkitDE instanceof org.bukkit.entity.Player p) {
            p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.75F, 1.25F);
            p.playSound(p.getLocation(), Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS, 0.75F, 1.25F);
        }

        this.discard();
    }

}
