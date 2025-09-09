package org.blackstamp.sleepyChronicles.listener.item.misc.sniperRifle;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.summonableMob;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

@Registrable
public class onDamageToE implements Listener {

    @EventHandler
    private void onEntityDamage(ProjectileHitEvent e) {
        globalClass global = new globalClass();
        Entity projectileEntity = e.getEntity();
        LivingEntity projectileShooter = (LivingEntity) e.getEntity().getShooter();
        LivingEntity damagedEntity = (LivingEntity) e.getHitEntity();

        if(projectileShooter == null) return;
        if(damagedEntity == null) return;
        if(!(projectileShooter instanceof Player p)) return;
        if(damagedEntity instanceof Player) return;

        if(!(damagedEntity instanceof CraftEntity craftEntity)) return;
        net.minecraft.world.entity.Entity nmsEntity = craftEntity.getHandle();

        if(nmsEntity instanceof summonableMob) return;

        if(projectileEntity.getScoreboardTags().contains("sniperProjectile")) {
            damagedEntity.damage(20);
            global.spawnParticles(damagedEntity.getLocation(), Particle.BLOCK, Material.REDSTONE_BLOCK, 25);
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,0.75F,1.5F);

            }
    }
}


