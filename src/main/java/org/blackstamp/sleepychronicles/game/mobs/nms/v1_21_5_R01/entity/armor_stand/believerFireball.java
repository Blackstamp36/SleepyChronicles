package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.armor_stand;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.bossMob;
import org.blackstamp.sleepychronicles.api.mobs.projectile.CustomProjectile;
import org.blackstamp.sleepychronicles.api.particle.ParticleManager;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class believerFireball extends ArmorStand implements CustomProjectile {
    private org.bukkit.entity.LivingEntity bukkitE = this.getBukkitLivingEntity();
    private LivingEntity caster;
    private int lifetimeTicks;
    private int tickCount;
    private final int projectileDamage;
    private final int particleCount = 4;
    private final int fireTicks = 100;
    private final double blocksPerTick = 0.5;

    private final Vec3 direction;

    public believerFireball(EntityType<? extends ArmorStand> entityType, Level level,
                            int projectileDamage, int lifetimeTicks, LivingEntity caster,
                            double degrees) {
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

        if(tickCount % 3 == 0) {
            ParticleManager pM = new ParticleManager(bukkitE.getWorld());
            Location projectileLoc = new Location(bukkitE.getWorld(), bukkitE.getX(), bukkitE.getY() + 1.25, bukkitE.getZ());
            pM.particle(projectileLoc, Particle.GUST, null,
                    1, 0.0, 0.0, 0.0, 0.5);
            pM.particle(projectileLoc, Particle.FLAME, null,
                    particleCount, 0.25, 0.5, 0.25, 0.0);
        }

        Vec3 movement = new Vec3(direction.x() * blocksPerTick, 0, direction.z() * blocksPerTick);
        setPos(position().add(movement));

        if(!cM.getPlayerCollisions(this).isEmpty() || tickCount >= lifetimeTicks) {
            handleFireballImpact();
            this.discard();
        }
    }

    private void handleFireballImpact(){
        ParticleManager pM = new ParticleManager(bukkitE.getWorld());
        Location projectileLoc = new Location(bukkitE.getWorld(), bukkitE.getX(), bukkitE.getY() + 1.25, bukkitE.getZ());
        pM.particle(projectileLoc, Particle.EXPLOSION_EMITTER, null,
                1,0.0,0.0,0.0,0.5);

        for (org.bukkit.entity.Entity n : this.getBukkitLivingEntity().getLocation().getNearbyEntities(3, 1d, 3)) {
            if (!(n instanceof org.bukkit.entity.LivingEntity bukkitDE)) continue;
            if (n instanceof bossMob) continue;

            bukkitDE.damage(projectileDamage, caster.getBukkitLivingEntity());
            bukkitDE.setFireTicks(fireTicks);
            bukkitDE.addPotionEffect(new PotionEffect(PotionEffectType.WIND_CHARGED, 200, 0));

            if (n instanceof Player p) {
                p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.75F, 1.25F);
                p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 0.25F, 0.75F);
            }
        }

    }

    @Override
    public void handleImpact(LivingEntity damagedEntity) {
    }
}
