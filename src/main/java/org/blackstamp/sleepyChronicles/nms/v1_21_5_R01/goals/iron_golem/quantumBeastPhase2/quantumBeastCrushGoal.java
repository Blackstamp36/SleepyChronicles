package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.iron_golem.quantumBeastPhase2;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumBeast;
import org.blackstamp.sleepyChronicles.util.manager.ParticleManager;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.util.Vector;

public class quantumBeastCrushGoal extends Goal {
    double crushDamage = 21;
    int crushCooldown = 0;

    private final quantumBeast entity;

    public quantumBeastCrushGoal(quantumBeast entity){
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        LivingEntity target = entity.getTarget();
        org.bukkit.entity.LivingEntity bukkitEntity = entity.getBukkitLivingEntity();

        if(target == null) return false;
        if(!(target instanceof Player p)) return false;

        org.bukkit.entity.Player bukkitP = (org.bukkit.entity.Player) p.getBukkitLivingEntity();
        double distanceToTarget = bukkitEntity.getLocation().distance(bukkitP.getLocation());

        return !(distanceToTarget >= 4.5);
    }

    @Override
    public void tick() {
        LivingEntity target = entity.getTarget();

        if(!(target instanceof Player p)) return;

        if (crushCooldown <= 0) {
            crushPlayer(p);
            crushCooldown = 5;

        } else crushCooldown--;
    }

    private void crushPlayer(Player p) {
        org.bukkit.entity.Player bukkitP = (org.bukkit.entity.Player) p.getBukkitLivingEntity();
        org.bukkit.entity.LivingEntity bukkitE = entity.getBukkitLivingEntity();
        ParticleManager particleManager = new ParticleManager(bukkitE.getWorld());
        Location pLoc = bukkitP.getLocation();
        Location eLoc = bukkitE.getLocation();
        Vector direction = pLoc.toVector().subtract(eLoc.toVector()).normalize();

        Vector velocity = new Vector(
                direction.getX() * 2.5,
                1.85,
                direction.getZ() * 2.5
        ).add(new Vector(
                (Math.random() - 0.5) * 0.9,
                0,
                (Math.random() - 0.5) * 0.9
        ));

        if (pLoc.distanceSquared(eLoc) < 0.0001) return;

        entity.getBukkitLivingEntity().swingMainHand();
        bukkitP.setVelocity(velocity);
        bukkitP.damage(crushDamage, bukkitE);
        bukkitP.playSound(pLoc, Sound.BLOCK_ANVIL_PLACE, 0.85F, 0.15F);
        bukkitP.playSound(pLoc, Sound.ITEM_TRIDENT_THUNDER, 0.85F, 0.15F);
        bukkitP.playSound(pLoc, Sound.ENTITY_GENERIC_EXPLODE, 0.85F, 0.5F);
        particleManager.spawnParticle(pLoc, Particle.EXPLOSION, null,
                25, 0.15,0.15,0.15,1.0);
    }
}
