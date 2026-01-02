package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.ghast.eodPhase2;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.ghast.emperorOfDarkness;
import org.blackstamp.sleepychronicles.global.utils.manager.ParticleManager;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

import java.util.Random;

public class eodRandomTeleportGoal extends Goal {
    private final Random r = new Random();

    private final emperorOfDarkness entity;
    private final int teleportRadius;
    private final int explosionDamage;
    private final int tickCooldown;

    public eodRandomTeleportGoal(emperorOfDarkness entity, int teleportRadius,
                                 int explosionDamage, int tickCooldown) {
        this.entity = entity;
        this.teleportRadius = teleportRadius;
        this.explosionDamage = explosionDamage;
        this.tickCooldown = tickCooldown;
    }

    @Override
    public boolean canUse() {
        net.minecraft.world.entity.LivingEntity target = entity.getTarget();

        return target != null
                && entity.currentAttack.equals(emperorOfDarkness.phase2Attacks.RANDOM_TELEPORT)
                && entity.getTickCooldown() <= 0;
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public void start() {
        entity.increaseTickCooldown(tickCooldown);
    }

    @Override
    public void stop(){
        entity.currentAttack = emperorOfDarkness.phase2Attacks.LEVITATION_SPELL;
    }

    @Override
    public void tick() {
        net.minecraft.world.entity.LivingEntity target = entity.getTarget();
        if (target == null) return;

        executeExplosionTeleport();
    }

    private void executeExplosionTeleport(){
        LivingEntity bukkitE = entity.getBukkitLivingEntity();
        World world = bukkitE.getWorld();
        Vec3 startPos = new Vec3(entity.getX(), entity.getY(), entity.getZ());

        double angle = r.nextDouble() * 2 * Math.PI;
        double distance = r.nextDouble() * teleportRadius;

        double x = startPos.x() + Math.cos(angle) * distance;
        double y = startPos.y();
        double z = startPos.z() + Math.sin(angle) * distance;

        Location newPos = new Location(world, x, y, z);

        if(isLocationSafe(newPos)) {
            entity.teleportTo(newPos.x(), newPos.y(), newPos.getZ());
            generateExplosionSphere();
            }
        }

    private void generateExplosionSphere() {
        LivingEntity bukkitE = entity.getBukkitLivingEntity();
        World world = bukkitE.getWorld();
        ParticleManager pM = new ParticleManager(world);

        new BukkitRunnable() {
            int tickCount = 0;

            @Override
            public void run() {
                Location currentLoc = bukkitE.getLocation();

                if (tickCount++ >= 60) {
                    pM.spawnParticle(currentLoc, Particle.EXPLOSION_EMITTER, null,
                            10, 2.0, 2.0, 2.0, 1.0);
                    for (org.bukkit.entity.Player nearby : entity.getBukkitLivingEntity().getLocation().getNearbyPlayers(12)) {
                        nearby.damage(explosionDamage, entity.getBukkitLivingEntity());
                        nearby.playSound(nearby.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.85F, 0.75F);
                        nearby.playSound(nearby.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 0.85F, 1.75F);
                    }

                    this.cancel();
                }

                if (tickCount % 20 == 0) {
                    pM.spawnSphere(currentLoc, Particle.END_ROD,
                            12, 500, 0.0, null);

                    for (org.bukkit.entity.Player nearby : entity.getBukkitLivingEntity().getLocation().getNearbyPlayers(24))
                        nearby.playSound(nearby.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 0.85F, 1.75F);

                }
            }

        }.runTaskTimer(SleepyChronicles.getInstance(), 0, 1);
    }

    private boolean isLocationSafe(Location targetLoc) {
        LivingEntity bukkitE = entity.getBukkitLivingEntity();
        World world = bukkitE.getWorld();

        BoundingBox entityBox = bukkitE.getBoundingBox().shift(targetLoc.clone().subtract(bukkitE.getLocation()));

        for (double x = entityBox.getMinX(); x < entityBox.getMaxX(); x += 0.5) {
            for (double y = entityBox.getMinY(); y < entityBox.getMaxY(); y += 0.5) {
                for (double z = entityBox.getMinZ(); z < entityBox.getMaxZ(); z += 0.5) {
                    Location checkPoint = new Location(world, x, y, z);
                    if (world.getBlockAt(checkPoint).getType().isSolid()) return false;
                }
            }
        }

        return true;
    }
}
