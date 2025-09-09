package org.blackstamp.sleepyChronicles.listener.entity.boss;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.bossMob;
import org.blackstamp.sleepyChronicles.util.color.ChatColor;
import org.blackstamp.sleepyChronicles.util.manager.ParticleManager;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.*;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import static org.blackstamp.sleepyChronicles.sleepyChronicles.chatPrefix;

@Registrable
public class onEntityDeath implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        ParticleManager particleManager = new ParticleManager(e.getEntity().getWorld());
        LivingEntity entity = e.getEntity();
        Location l = entity.getLocation();
        globalClass global = new globalClass();
        CraftLivingEntity craftEntity = (CraftLivingEntity) entity;
        net.minecraft.world.entity.LivingEntity nmsEntity = craftEntity.getHandle();

        if(nmsEntity instanceof bossMob) {
            for(Player nearby : l.getNearbyPlayers(35)) {
                nearby.playSound(nearby.getLocation(), Sound.ENTITY_WITHER_DEATH, 0.35F, 1.5F);
                nearby.playSound(nearby.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1.5F);
            }

            for(Player all : Bukkit.getOnlinePlayers()){
                all.sendMessage(chatPrefix + entity.getName() + ChatColor.of("#9c48dc") + " has been defeated!");
                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F,1.5F);
                global.spawnParticles(l, Particle.BLOCK, Material.REDSTONE_BLOCK, 50);
                particleManager.spawnParticle(l, Particle.HAPPY_VILLAGER,null,
                        15,0.25,0.25,0.25,1.0);
            }
        }
    }
}

