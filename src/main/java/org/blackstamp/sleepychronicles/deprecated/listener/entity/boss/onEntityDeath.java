package org.blackstamp.sleepychronicles.deprecated.listener.entity.boss;

import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.bossMob;
import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
import org.blackstamp.sleepychronicles.global.utils.manager.ParticleManager;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.*;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import static org.blackstamp.sleepychronicles.SleepyChronicles.chatPrefix;

@Registrable
public class onEntityDeath implements Listener {
    private final int particleCount = 15;

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        ParticleManager particleManager = new ParticleManager(e.getEntity().getWorld());
        LivingEntity entity = e.getEntity();
        Location l = entity.getLocation();
        CraftLivingEntity craftEntity = (CraftLivingEntity) entity;
        net.minecraft.world.entity.LivingEntity nmsEntity = craftEntity.getHandle();

        if(!(nmsEntity instanceof bossMob)) return;

        particleManager.spawnParticle(l, Particle.HAPPY_VILLAGER,null,
                particleCount,0.25,0.25,0.25,1.0);
        particleManager.spawnParticle(l, Particle.BLOCK,Material.REDSTONE_BLOCK.createBlockData(),
                particleCount,0.05,0.05,0.05,0.0);

        for(Player nearby : l.getNearbyPlayers(35)) {
            nearby.playSound(nearby.getLocation(), Sound.ENTITY_WITHER_DEATH, 0.35F, 1.5F);
            nearby.playSound(nearby.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1.5F);
        }

        for(Player all : Bukkit.getOnlinePlayers()){
            all.sendMessage(chatPrefix + entity.getName() + ChatColor.of("#9c48dc") + " has been defeated!");
            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.25F,1.5F);
        }

    }
}


