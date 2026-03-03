package org.blackstamp.sleepychronicles.game.listener.player.spectator;

import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@Registrable
public class DamageListener implements Listener {

    @EventHandler
    public void damage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player p)) return;
        if(!p.getGameMode().equals(GameMode.SPECTATOR)) return;
        if(!e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) return;

        e.setCancelled(true);
    }
}
