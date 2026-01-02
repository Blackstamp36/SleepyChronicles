package org.blackstamp.sleepychronicles.deprecated.listener.player;

import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@Registrable
public class onDamageToP implements Listener {

    @EventHandler
    private void onDamageToP(EntityDamageEvent e){
        Entity entity = e.getEntity();

        if(!(entity instanceof Player p)) return;

        if(p.getOpenInventory().getOriginalTitle().equals("TRINKETS"))
            p.getOpenInventory().close();
        }
    }

