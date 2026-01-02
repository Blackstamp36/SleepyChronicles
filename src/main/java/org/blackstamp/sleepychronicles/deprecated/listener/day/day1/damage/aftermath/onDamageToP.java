package org.blackstamp.sleepychronicles.deprecated.listener.day.day1.damage.aftermath;

import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Registrable
public class onDamageToP implements Listener {

    @EventHandler
    private void onDamageToP(EntityDamageEvent e){
        Entity entity = e.getEntity();
        World w = entity.getWorld();
        EntityDamageEvent.DamageCause damageCause = e.getCause();
        Location l = entity.getLocation();

        if(!(entity instanceof Player p)) return;
        if(!(damageCause.equals(EntityDamageEvent.DamageCause.VOID))) return;
        if(!(w.getName().equals("world_aftermath"))) return;

        double x = l.getX();
        double y = l.getY() + 225;
        double z = l.getZ();

        Location newLoc = new Location(Bukkit.getWorld("world"), x, y, z);
        p.teleport(newLoc);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING,400,0, true,false));
    }
}
