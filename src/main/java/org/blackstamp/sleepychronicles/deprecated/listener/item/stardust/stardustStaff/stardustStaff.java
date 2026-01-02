package org.blackstamp.sleepychronicles.deprecated.listener.item.stardust.stardustStaff;

import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Location;
import org.bukkit.entity.Enemy;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Registrable
public class stardustStaff implements Listener {
    GlobalClass global = new GlobalClass();

    @EventHandler
    private void onHit(EntityDamageEvent e) {
        Entity damagedEntity = e.getEntity();
        Entity causingEntity = e.getDamageSource().getCausingEntity();

        if(!(damagedEntity instanceof Enemy monster)) return;
        if(!(causingEntity instanceof Player p)) return;
        if(e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)) return;
        if(e.isCancelled()) return;

        ItemStack main = p.getInventory().getItemInMainHand();
        Location l = damagedEntity.getLocation();

        if(!global.isCustomItem(main, "stardust_staff")) return;

        monster.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS,100,1));
        monster.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,100,0));
        for(Player nearby : l.getNearbyPlayers(5)) nearby.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 0));
        }
    }