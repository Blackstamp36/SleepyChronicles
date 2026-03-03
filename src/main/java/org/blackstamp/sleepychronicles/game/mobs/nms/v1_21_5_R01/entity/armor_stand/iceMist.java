package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.armor_stand;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.api.mobs.projectile.CustomProjectile;
import org.blackstamp.sleepychronicles.api.particle.ParticleManager;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

public class iceMist extends ArmorStand implements CustomProjectile {
    private org.bukkit.entity.LivingEntity bukkitE = this.getBukkitLivingEntity();
    private LivingEntity caster;
    private int lifetimeTicks;
    Location targetPrevLoc;
    private int tickCount;
    private final int projectileDamage;
    private final int particleCount = 4;
    private final double blocksPerTick = 0.35;

    public iceMist(EntityType<? extends ArmorStand> entityType, Level level,
                   int projectileDamage, int lifetimeTicks, LivingEntity caster, LivingEntity target) {
        super(entityType, level);
        this.lifetimeTicks = lifetimeTicks;
        this.caster = caster;
        this.targetPrevLoc = target.getBukkitLivingEntity().getLocation();
        this.projectileDamage = (int) (projectileDamage * 1.5);

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
            pM.particle(projectileLoc, Particle.SNOWFLAKE, null,
                    particleCount, 0.25, 0.5, 0.25, 0.0);
        }

        Vector toTarget = targetPrevLoc.toVector().subtract(bukkitE.getLocation().toVector()).normalize();
        Vector currentVel = bukkitE.getVelocity();
        Vector newVel = currentVel.add(toTarget.multiply(0.6)).normalize().multiply(blocksPerTick);

        if(newVel.length() < 0.001) return;

        bukkitE.setVelocity(newVel);

        if(!cM.getPlayerCollisions(this).isEmpty() || tickCount >= lifetimeTicks) {
            handleIceMistImpact();
            this.discard();
        }
    }

    private void handleIceMistImpact(){
        ParticleManager pM = new ParticleManager(bukkitE.getWorld());
        Location projectileLoc = new Location(bukkitE.getWorld(), bukkitE.getX(), bukkitE.getY() + 1.25, bukkitE.getZ());
        pM.particle(projectileLoc, Particle.EXPLOSION_EMITTER, null,
                1,0.0,0.0,0.0,0.5);
        launchIceShards();
    }

    private void launchIceShards(){
        int degrees = 45;
        Level nmsLevel = this.level();
        final int maxDegrees = (90 * 4);
        final Vec3 startPos = new Vec3(this.getX(), this.getY(), this.getZ());

        for (int i = degrees; i <= maxDegrees; i += 45) {
            iceShard projectile = new iceShard(EntityType.ARMOR_STAND, nmsLevel,
                    projectileDamage, 30, caster, i);

            projectile.setPos(startPos);
            nmsLevel.addFreshEntity(projectile);
        }
    }

    @Override
    public void handleImpact(LivingEntity damagedEntity) {
    }
}
