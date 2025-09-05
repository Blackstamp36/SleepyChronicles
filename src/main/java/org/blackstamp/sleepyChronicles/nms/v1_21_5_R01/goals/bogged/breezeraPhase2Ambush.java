package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.bogged;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.bogged.breezeraBoss;
import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class breezeraPhase2Ambush extends Goal {

    private final breezeraBoss boss;
    private int ambushCooldown;

    public breezeraPhase2Ambush(breezeraBoss boss) {
        this.boss = boss;
        this.ambushCooldown = 0;
    }

    @Override
    public boolean canUse() {
        if(!(boss.getTarget() instanceof Player targetPlayer)) return false;
        org.bukkit.entity.Player bukkitPlayer = (org.bukkit.entity.Player) targetPlayer.getBukkitLivingEntity();
        LivingEntity bukkitBoss = boss.getBukkitLivingEntity();
        double distanceToTarget = bukkitBoss.getLocation().distance(bukkitPlayer.getLocation());

        return distanceToTarget >= 14.0
                && boss.getPhase() == 2
                && bukkitPlayer.getGameMode().equals(GameMode.SURVIVAL);
    }

    @Override
    public void tick() {
        net.minecraft.world.entity.LivingEntity target = boss.getTarget();

        if(!(target instanceof Player p)) return;

        if (ambushCooldown <= 0) {

            attemptAmbush(p);
            ambushCooldown = 0;
        } else {
            ambushCooldown--;
        }
    }

    private void attemptAmbush(net.minecraft.world.entity.player.Player target) {
        LivingEntity bukkitPlayer = target.getBukkitLivingEntity();
        LivingEntity bukkitBoss = boss.getBukkitLivingEntity();
        Vector behindPlayer = bukkitPlayer.getLocation().getDirection().multiply(-4);
        Location ambushSpot = bukkitPlayer.getLocation().add(behindPlayer);
        ambushSpot.setY(bukkitPlayer.getLocation().getY());

        if (ambushSpot.getBlock().getType().isSolid()) {
            return;
        }

        bukkitBoss.getWorld().spawnParticle(Particle.SMOKE, bukkitBoss.getLocation(),
                20, 0.5, 0.5, 0.5, 0.1);
        bukkitBoss.getWorld().playSound(bukkitBoss.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.5F);

        bukkitBoss.teleport(ambushSpot);


        Bukkit.getScheduler().runTaskLater(sleepyChronicles.getter(), () -> {
            if (!boss.isAlive()) return;
            bukkitBoss.getWorld().spawnParticle(Particle.SMOKE, bukkitBoss.getLocation(),
                    20, 0.5, 0.5, 0.5, 0.1);
            bukkitBoss.getWorld().playSound(bukkitBoss.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.5F);

            for (int i = 0; i < 4; i++) {
                boss.shootSeed(target, true);
            }
        }, 5L);
    }
}
