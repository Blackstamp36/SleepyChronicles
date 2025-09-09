package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.iron_golem.quantumBeastPhase2;

import net.minecraft.world.entity.ai.goal.Goal;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumBeast;
import org.blackstamp.sleepyChronicles.util.manager.ParticleManager;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class quantumBeastBoostGoal extends Goal {

    int speedAmplifier = 2;
    int boostCooldown = 0;
    int particleAmount = 250;

    private final quantumBeast entity;

    public quantumBeastBoostGoal(quantumBeast entity) {
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        net.minecraft.world.entity.LivingEntity target = entity.getTarget();

        if(target == null) return false;

        org.bukkit.entity.LivingEntity bukkitT = target.getBukkitLivingEntity();
        org.bukkit.entity.LivingEntity bukkitE = entity.getBukkitLivingEntity();

        double distanceToTarget = bukkitE.getLocation().distance(bukkitT.getLocation());

        return distanceToTarget >= 14.0
                && entity.hasLineOfSight(target);
    }

    @Override
    public void tick() {
        net.minecraft.world.entity.LivingEntity target = entity.getTarget();
        org.bukkit.entity.LivingEntity bukkitE = entity.getBukkitLivingEntity();

        if(target == null) return;

        if (boostCooldown <= 0) {
            incrementSpeedBoost(bukkitE);
            boostCooldown = 100;

        } else boostCooldown--;
    }

    private void incrementSpeedBoost(LivingEntity bukkitE){
        ParticleManager pM = new ParticleManager(bukkitE.getWorld());
        Location l = bukkitE.getLocation();

        pM.spawnParticle(l, Particle.EFFECT, null
                ,particleAmount,0.5,0.85,0.5,0.65);
        bukkitE.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,100, speedAmplifier));

        for(Player p : l.getNearbyPlayers(25)){
            p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_DRINK,0.75F,1.5F);
            p.playSound(p.getLocation(), Sound.ENTITY_WANDERING_TRADER_DRINK_POTION,0.75F,1.5F);
            p.playSound(p.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL,0.75F,0.5F);
        }
    }
}
