package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.skeleton.ptPhase1;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.skeleton.planterrorBoss;
import org.blackstamp.sleepychronicles.global.utils.manager.ParticleManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ptPoisonAttackGoal extends Goal {

    private final planterrorBoss entity;
    private final int poisonDamage;
    private final int tickCooldown;
    private final int poisonTicks;

    public ptPoisonAttackGoal(planterrorBoss entity, int poisonDamage,
                              int poisonTicks, int tickCooldown) {
        this.entity = entity;
        this.poisonDamage = poisonDamage;
        this.poisonTicks = poisonTicks;
        this.tickCooldown = tickCooldown;
    }

    @Override
    public boolean canUse() {
        LivingEntity target = entity.getTarget();

        return target != null
                && entity.currentAttack.equals(planterrorBoss.bossAttacks.POISON_ATTACK)
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
        entity.currentAttack = planterrorBoss.bossAttacks.ALL_DIRECTIONS_SEEDS;
    }

    @Override
    public void tick(){
        LivingEntity target = entity.getTarget();
        if(target == null) return;

        executePoisonNearby(entity);
    }

    private void executePoisonNearby(Mob entity){
        Location l = entity.getBukkitLivingEntity().getLocation();
        Location spawnLoc = new Location(l.getWorld(), l.getX(), l.getY() + 3, l.getZ());
        ParticleManager pM = new ParticleManager(entity.getBukkitLivingEntity().getWorld());
        org.bukkit.entity.Player bukkitT = (org.bukkit.entity.Player) entity.getTarget().getBukkitLivingEntity();
        bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS,0.5F,1.25F);

        entity.getBukkitLivingEntity().addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE,60,2));
        pM.spawnParticle(l, Particle.ENTITY_EFFECT, Color.fromRGB(95,148,12),
                500,1.75,1.75,1.75,0.15);

        pM.spawnCircle(spawnLoc, Particle.FALLING_SPORE_BLOSSOM,
                5,50,0.0,null);
        pM.spawnCircle(spawnLoc, Particle.CHERRY_LEAVES,
                5,50,0.0,null);

        for(Player nearby : l.getNearbyPlayers(5,5,5)){
            nearby.damage(poisonDamage, entity.getBukkitLivingEntity());
            nearby.addPotionEffect(new PotionEffect(PotionEffectType.POISON,poisonTicks,2,false,false));
            nearby.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,20,0,false,false));
            nearby.playSound(l, Sound.ENTITY_ALLAY_ITEM_GIVEN,0.85F,1.75F);
        }

    }
}
