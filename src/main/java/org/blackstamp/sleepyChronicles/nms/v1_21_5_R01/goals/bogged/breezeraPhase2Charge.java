package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.bogged;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.bogged.breezeraBoss;
import org.bukkit.GameMode;
import org.bukkit.Sound;

import java.util.Random;

public class breezeraPhase2Charge extends Goal {
    Random r = new Random();
    private final breezeraBoss boss;
    private int cooldown = 0;

    public breezeraPhase2Charge(breezeraBoss boss) {
        this.boss = boss;
    }

    @Override
    public boolean canUse() {
        if(!(boss.getTarget() instanceof Player targetPlayer)) return false;
        org.bukkit.entity.Player bukkitPlayer = (org.bukkit.entity.Player) targetPlayer.getBukkitLivingEntity();
        org.bukkit.entity.LivingEntity bukkitBoss = boss.getBukkitLivingEntity();
        double distanceToTarget = bukkitBoss.getLocation().distance(bukkitPlayer.getLocation());

        return distanceToTarget <= 7.0
                && boss.getPhase() == 2
                && bukkitPlayer.getGameMode().equals(GameMode.SURVIVAL);
    }

    @Override
    public void tick() {
        LivingEntity target = boss.getTarget();
        org.bukkit.entity.LivingEntity bukkitBoss = boss.getBukkitLivingEntity();
        if (!(target instanceof Player p)) return;
        org.bukkit.entity.Player bukkitPlayer = (org.bukkit.entity.Player) p.getBukkitLivingEntity();
        if (!bukkitPlayer.getGameMode().equals(GameMode.SURVIVAL)) return;

        double actualDistance = bukkitBoss.getLocation().distance(bukkitPlayer.getLocation());

        if (actualDistance > 4.0) {
            boss.getNavigation().moveTo(target, 1.5);

        } else {
            boss.getNavigation().stop();

            if (cooldown <= 0) {
                int randomDamage = r.nextInt(16,33);
                bukkitBoss.swingMainHand();
                bukkitPlayer.damage(randomDamage, bukkitBoss);
                bukkitPlayer.playSound(bukkitPlayer.getLocation(), Sound.ENTITY_ALLAY_ITEM_TAKEN,0.85F,1.25F);
                cooldown = 20;
            }

        }
        cooldown--;
    }
}