package org.blackstamp.sleepyChronicles.listener.entity.bosses;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import static org.blackstamp.sleepyChronicles.sleepyChronicles.PREFIX;

@Registrable
public class onEntitySpawn implements Listener {

    @EventHandler
    private void onEntitySpawn(CreatureSpawnEvent e) {
        Location l = e.getLocation();
        globalClass global = new globalClass();
        LivingEntity entity = e.getEntity();
        CraftLivingEntity craftEntity = (CraftLivingEntity) entity;
        net.minecraft.world.entity.LivingEntity nmsEntity = craftEntity.getHandle();

        if(entity.getScoreboardTags().contains("boss")) {
            global.spawnParticles(l, Particle.ENCHANTED_HIT, null, 50);

                for(Player all : Bukkit.getOnlinePlayers()){
                    all.sendMessage(PREFIX + entity.getName() + ChatColor.of("#9c48dc") + " has awoken!");
                    all.playSound(all.getLocation(), Sound.ENTITY_RAVAGER_ROAR, 0.5F,1.5F);

                }
            }
        }
    }
