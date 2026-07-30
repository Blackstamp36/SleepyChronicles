package org.blackstamp.sleepychronicles.game.listener;

import org.blackstamp.sleepychronicles.game.spawn.SpawnManager;
import org.blackstamp.sleepychronicles.game.spawn.interfaces.SleepyAttack;
import org.blackstamp.sleepychronicles.game.spawn.interfaces.SleepyDamageable;
import org.blackstamp.sleepychronicles.game.spawn.interfaces.SleepyLootable;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

@Registrable
public class MobListener implements Listener {

    @EventHandler
    public void spawn(CreatureSpawnEvent e){ SpawnManager.getInstance().spawn(e); }

    @EventHandler
    public void attack(EntityDamageByEntityEvent e){
        Entity mob = e.getDamager();

        if(!(mob instanceof LivingEntity)) return;

        net.minecraft.world.entity.LivingEntity nms = ((CraftLivingEntity) mob).getHandle();

        if(!(nms instanceof SleepyAttack attack)) return;

        attack.handleAttack(e);
    }

    @EventHandler
    public void damage(EntityDamageByEntityEvent e){
        Entity mob = e.getEntity();

        if(!(mob instanceof LivingEntity)) return;

        net.minecraft.world.entity.LivingEntity nms = ((CraftLivingEntity) mob).getHandle();

        if(!(nms instanceof SleepyDamageable damageable)) return;

        damageable.handleDamage(e);
    }

    @EventHandler
    public void death(EntityDeathEvent e){
        LivingEntity mob = e.getEntity();
        net.minecraft.world.entity.LivingEntity nms = ((CraftLivingEntity) mob).getHandle();

        if(!(nms instanceof SleepyLootable lootable)) return;
        lootable.onDeath();
    }
}