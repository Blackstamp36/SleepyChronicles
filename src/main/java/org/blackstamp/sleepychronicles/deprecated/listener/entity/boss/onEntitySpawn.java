package org.blackstamp.sleepychronicles.deprecated.listener.entity.boss;

import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.bossMob;
import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
import org.blackstamp.sleepychronicles.global.utils.manager.ParticleManager;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
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

import static org.blackstamp.sleepychronicles.SleepyChronicles.chatPrefix;

@Registrable
public class onEntitySpawn implements Listener {

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent e) {
        Location l = e.getLocation();
        ParticleManager pM = new ParticleManager(l.getWorld());
        GlobalClass global = new GlobalClass();
        LivingEntity entity = e.getEntity();
        CraftLivingEntity craftEntity = (CraftLivingEntity) entity;
        net.minecraft.world.entity.LivingEntity nmsEntity = craftEntity.getHandle();

        if(!(nmsEntity instanceof bossMob)) return;

        pM.spawnParticle(l, Particle.ENCHANTED_HIT,null,
                25,0.25,0.25,0.25,1.0);
        global.modifyBossHealth(nmsEntity);

        for(Player all : Bukkit.getOnlinePlayers()){
            all.sendMessage(chatPrefix + entity.getName() + ChatColor.of("#9c48dc") + " has awoken!");
            all.playSound(all.getLocation(), Sound.ENTITY_RAVAGER_ROAR, 0.25F,1.5F);

        }
    }
}
