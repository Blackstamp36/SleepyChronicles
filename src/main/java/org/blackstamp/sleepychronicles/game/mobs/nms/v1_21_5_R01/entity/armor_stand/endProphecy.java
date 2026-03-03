package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.armor_stand;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.bossMob;
import org.blackstamp.sleepychronicles.api.mobs.projectile.CustomProjectile;
import org.blackstamp.sleepychronicles.api.particle.ParticleManager;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class endProphecy extends ArmorStand implements CustomProjectile {
    private org.bukkit.entity.LivingEntity bukkitE = this.getBukkitLivingEntity();
    private LivingEntity caster;
    private int lifetimeTicks;
    private int tickCount;
    private final int projectileDamage;
    private final int particleCount = 4;
    private static final double blocksPerTick = 0.45;

    private final Vec3 direction;

    public endProphecy(EntityType<? extends ArmorStand> entityType, Level level,
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

        if (tickCount % 3 == 0) {
            ParticleManager pM = new ParticleManager(bukkitE.getWorld());
            Location projectileLoc = new Location(bukkitE.getWorld(), bukkitE.getX(), bukkitE.getY() + 1.25, bukkitE.getZ());
            pM.particle(projectileLoc, Particle.EXPLOSION, null,
                    1, 0.0, 0.0, 0.0, 0.0);
            pM.particle(projectileLoc, Particle.CRIT, null,
                    particleCount, 0.25, 0.5, 0.25, 0.0);
            pM.particle(projectileLoc, Particle.ENTITY_EFFECT, Color.fromRGB(133,89,243),
                    particleCount, 0.0, 0.0, 0.0, 0.0); // #8459f3
        }

        Vec3 movement = new Vec3(direction.x() * blocksPerTick, 0, direction.z() * blocksPerTick);
        setPos(position().add(movement));

        if(!cM.getPlayerCollisions(this).isEmpty() || tickCount >= lifetimeTicks) {
            for(Entity e : cM.getPlayerCollisions(this)) handleImpact((LivingEntity) e);
            this.discard();
        }
    }

    @Override
    public void handleImpact(LivingEntity damagedEntity) {
        for (org.bukkit.entity.Entity n : this.getBukkitLivingEntity().getLocation().getNearbyEntities(3, 1, 3)) {
            if (!(n instanceof org.bukkit.entity.LivingEntity bukkitDE)) continue;
            if (n instanceof bossMob) continue;

            bukkitDE.damage(projectileDamage, caster.getBukkitLivingEntity());

            if (n instanceof Player p) {
                p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.75F, 1.5F);
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_HURT_FREEZE, 0.25F, 0.75F);
            }
        }
    }
}
