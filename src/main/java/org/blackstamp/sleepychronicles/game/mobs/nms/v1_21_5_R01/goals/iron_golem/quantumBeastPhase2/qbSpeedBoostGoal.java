package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.iron_golem.quantumBeastPhase2;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumBeast;
import org.blackstamp.sleepychronicles.global.utils.manager.ParticleManager;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class qbSpeedBoostGoal extends Goal {

    private final int speedAmplifier;
    private final int tickCooldown;
    private final int particleCount = 200;

    private final quantumBeast entity;

    public qbSpeedBoostGoal(quantumBeast entity, int speedAmplifier,
                             int tickCooldown) {
        this.entity = entity;
        this.speedAmplifier = speedAmplifier;
        this.tickCooldown = tickCooldown;
    }

    @Override
    public boolean canUse() {
        net.minecraft.world.entity.LivingEntity target = entity.getTarget();

        return target != null
                && entity.currentAttack.equals(quantumBeast.bossAttacks.SPEED_BOOST)
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
        entity.currentAttack = quantumBeast.bossAttacks.EARTHQUAKE;
    }

    @Override
    public void tick(){
        net.minecraft.world.entity.LivingEntity target = entity.getTarget();
        if(target == null) return;

        grantSpeedBoost(entity);
    }

    private void grantSpeedBoost(Mob entity){
        LivingEntity bukkitE = entity.getBukkitLivingEntity();
        ParticleManager pM = new ParticleManager(bukkitE.getWorld());
        Location l = bukkitE.getLocation();

        pM.spawnParticle(l, Particle.ENTITY_EFFECT, Color.AQUA,
                particleCount,0.5,1.25,0.5,0.25);
        bukkitE.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,100, speedAmplifier));
        bukkitE.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,100, 0, false,false));

        for(Player p : l.getNearbyPlayers(32)){
            p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_DRINK,0.75F,1.5F);
            p.playSound(p.getLocation(), Sound.ENTITY_WANDERING_TRADER_DRINK_POTION,0.75F,1.5F);
            p.playSound(p.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL,0.75F,0.5F);
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_FALL,0.75F,0.5F);
        }
    }
}
