package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.iron_golem.quantumBeastPhase1;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumBeast;
import org.blackstamp.sleepyChronicles.util.manager.ParticleManager;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class quantumBeastBarracudaGoal extends Goal {
    double liftDamage = 18;
    int liftCooldown = 0;

    private final quantumBeast entity;

    public quantumBeastBarracudaGoal(quantumBeast entity){
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

        return !(distanceToTarget >= 5.75);
    }

    @Override
    public void tick() {
        net.minecraft.world.entity.LivingEntity target = entity.getTarget();

        if(!(target instanceof Player p)) return;

        if (liftCooldown <= 0) {
            liftPlayer(p);
            liftCooldown = 10;

        } else liftCooldown--;
    }

    private void liftPlayer(Player p) {
        org.bukkit.entity.Player bukkitP = (org.bukkit.entity.Player) p.getBukkitLivingEntity();
        org.bukkit.entity.LivingEntity bukkitE = entity.getBukkitLivingEntity();
        ParticleManager particleManager = new ParticleManager(bukkitE.getWorld());
        Location pLoc = bukkitP.getLocation();
        Location eLoc = bukkitE.getLocation();
        Vector direction = pLoc.toVector().subtract(eLoc.toVector()).normalize();

        Vector velocity = new Vector(
                direction.getX() * 2.25,
                1.55,
                direction.getZ() * 2.25
        ).add(new Vector(
                (Math.random() - 0.5) * 0.3,
                0,
                (Math.random() - 0.5) * 0.3
        ));

        if (pLoc.distanceSquared(eLoc) < 0.0001) return;

        entity.getBukkitLivingEntity().swingMainHand();
        bukkitP.setVelocity(velocity);
        bukkitP.damage(liftDamage, bukkitE);
        bukkitP.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 40, 0,false,false));
        bukkitP.playSound(pLoc, Sound.BLOCK_ANVIL_PLACE, 0.85F, 0.75F);
        bukkitP.playSound(pLoc, Sound.ENTITY_WITHER_BREAK_BLOCK, 0.85F, 0.5F);
        particleManager.spawnParticle(pLoc, Particle.EXPLOSION, null,
                15, 0.15,0.15,0.15,1.0);
    }
}
