package org.blackstamp.sleepychronicles.game.effect;

import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

@Registrable
public class InvincibilityEffect implements Listener {

    @EventHandler
    public void effect(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player p)) return;
        if(!p.hasPotionEffect(PotionEffectType.LUCK)) return;

        e.setCancelled(true);
    }
}