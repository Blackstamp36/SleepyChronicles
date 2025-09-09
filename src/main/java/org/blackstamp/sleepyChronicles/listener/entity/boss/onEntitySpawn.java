package org.blackstamp.sleepyChronicles.listener.entity.boss;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.bossMob;
import org.blackstamp.sleepyChronicles.util.color.ChatColor;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
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

import static org.blackstamp.sleepyChronicles.sleepyChronicles.chatPrefix;

@Registrable
public class onEntitySpawn implements Listener {

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent e) {
        Location l = e.getLocation();
        globalClass global = new globalClass();
        LivingEntity entity = e.getEntity();
        CraftLivingEntity craftEntity = (CraftLivingEntity) entity;
        net.minecraft.world.entity.LivingEntity nmsEntity = craftEntity.getHandle();

        if(nmsEntity instanceof bossMob) {
            global.spawnParticles(l, Particle.ENCHANTED_HIT, null, 50);
            global.modifyBossHealth(nmsEntity);

                for(Player all : Bukkit.getOnlinePlayers()){
                    all.sendMessage(chatPrefix + entity.getName() + ChatColor.of("#9c48dc") + " has awoken!");
                    all.playSound(all.getLocation(), Sound.ENTITY_RAVAGER_ROAR, 0.5F,1.5F);
                }
            }
        }
    }
