package org.blackstamp.sleepyChronicles.listener.entity.boss.quantumBeast.quantumCore;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.summonableMob;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

@Registrable
public class onProjectileHit implements Listener {
    double baseProjectileDamage = 8;

@EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
    Entity projectile = e.getEntity();

    if (!(e.getHitEntity() instanceof CraftEntity craftHitEntity)) return;
    if (!(craftHitEntity.getHandle() instanceof LivingEntity hitEntity)) return;
    if (!projectile.getScoreboardTags().contains("quantumProjectile")) return;

    if (hitEntity instanceof Player || hitEntity instanceof summonableMob) {
        org.bukkit.entity.LivingEntity bukkitHitEntity = hitEntity.getBukkitLivingEntity();
        bukkitHitEntity.damage(baseProjectileDamage);
        }

    }
}
