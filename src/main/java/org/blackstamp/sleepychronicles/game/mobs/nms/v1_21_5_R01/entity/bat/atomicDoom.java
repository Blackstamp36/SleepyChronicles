package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.bat;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.armor_stand.endProphecy;
import org.blackstamp.sleepychronicles.api.mobs.projectile.CustomProjectile;
import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
import org.blackstamp.sleepychronicles.api.particle.ParticleManager;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.bukkit.util.Vector;

public class atomicDoom extends Bat implements CustomProjectile {
    private org.bukkit.entity.LivingEntity bukkitE = this.getBukkitLivingEntity();
    private final Location targetPrevLoc;
    private LivingEntity caster;
    private int lifetimeTicks;
    private int tickCount;
    private final int projectileDamage = 60;
    private final int particleCount = 4;
    private final double blocksPerTick = 0.5;

    public atomicDoom(EntityType<? extends Bat> entityType, Level level,
                      int lifetimeTicks, LivingEntity caster,
                      LivingEntity target) {
        super(entityType, level);
        this.lifetimeTicks = lifetimeTicks;
        this.caster = caster;
        this.targetPrevLoc = target.getBukkitLivingEntity().getLocation();

        registerAttributes();
    }

    public void registerAttributes(){
        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#6934ef") + "Atomic Doom"));

        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1);
        this.setHealth(this.getMaxHealth());

        this.setSilent(true);
    }

    @Override
    public void tick() {
        super.tick();
        Location projectileLoc = new Location(bukkitE.getWorld(), bukkitE.getX(), bukkitE.getY() + 1.25, bukkitE.getZ());
        tickCount++;

        if(tickCount % 3 == 0) {
            ParticleManager pM = new ParticleManager(bukkitE.getWorld());
            pM.particle(projectileLoc, Particle.GUST, null,
                    1, 0.0, 0.0, 0.0, 0.5);
            pM.particle(projectileLoc, Particle.WITCH, null,
                    particleCount, 0.25, 0.5, 0.25, 0.0);
            pM.particle(projectileLoc, Particle.RAID_OMEN, null,
                    particleCount, 0.25, 0.5, 0.25, 0.0);
        }

        Vector toTarget = targetPrevLoc.toVector().subtract(projectileLoc.toVector()).normalize();
        Vector currentVel = bukkitE.getVelocity();
        Vector newVel = currentVel.add(toTarget.multiply(0.6)).normalize().multiply(blocksPerTick);

        if(newVel.length() < 0.001) return;

        bukkitE.setVelocity(newVel);

        if(!cM.getPlayerCollisions(this).isEmpty() || tickCount >= lifetimeTicks) {
            handleAreaImpact();
            this.discard();
        }
    }

    private void handleAreaImpact(){
        ParticleManager pM = new ParticleManager(bukkitE.getWorld());
        Location projectileLoc = new Location(bukkitE.getWorld(), bukkitE.getX(), bukkitE.getY() + 1.25, bukkitE.getZ());
        pM.particle(projectileLoc, Particle.EXPLOSION_EMITTER, null,
                1,0.0,0.0,0.0,0.5);

        fireEndProphecies();
    }

    private void fireEndProphecies(){
        Level nmsLevel = this.level();
        final Vec3 startPos = new Vec3(this.getX(), this.getY(), this.getZ());

        for(int i = 90; i <= 360; i += 90){
            endProphecy projectile = new endProphecy(EntityType.ARMOR_STAND, nmsLevel,
                    projectileDamage, 20, caster, i);

            projectile.setPos(startPos);
            nmsLevel.addFreshEntity(projectile);
        }
    }

    @Override
    public void handleImpact(LivingEntity damagedEntity) {
    }
}
