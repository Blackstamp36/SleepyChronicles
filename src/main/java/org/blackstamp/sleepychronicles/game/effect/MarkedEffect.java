package org.blackstamp.sleepychronicles.game.effect;

import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

@Registrable
public class MarkedEffect implements Listener {

    private static final double DAMAGE_MULTIPLIER = 1.75D;

    @EventHandler
    public void effect(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player p)) return;
        if(!p.hasPotionEffect(PotionEffectType.UNLUCK)) return;

        int amplifier = p.getPotionEffect(PotionEffectType.UNLUCK).getAmplifier();
        if(amplifier <= 0) amplifier = 1;

        e.setDamage(e.getDamage() * (DAMAGE_MULTIPLIER * amplifier));
    }
}