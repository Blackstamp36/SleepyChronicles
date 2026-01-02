package org.blackstamp.sleepychronicles.deprecated.listener.day.day3.damage.fall;

import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@Registrable
public class onDamageToP implements Listener {

    @EventHandler
    private void onPDamage(EntityDamageEvent e) {
        GlobalClass global = new GlobalClass();
        Entity entity = e.getEntity();
        EntityDamageEvent.DamageCause damageCause = e.getCause();

        if(!(entity instanceof Player)) return;
        if(!(damageCause.equals(EntityDamageEvent.DamageCause.FALL))) return;
        if(!(global.getServerDay() >= 3)) return;

        e.setDamage(e.getDamage() * 3);
        }
    }

