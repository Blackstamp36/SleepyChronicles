package org.blackstamp.sleepychronicles.deprecated.listener.day.day1.damage.potion_effect;

import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

@Registrable
public class onDamageToP implements Listener {

    @EventHandler
    private void onPlayerDamage(EntityDamageEvent e) {
        Entity entity = e.getEntity();

        if(!(entity instanceof Player p)) return;

        if(p.hasPotionEffect(PotionEffectType.LUCK)) e.setCancelled(true);

        else if(p.hasPotionEffect(PotionEffectType.UNLUCK)){
            e.setDamage((e.getDamage() + 1.5) * p.getPotionEffect(PotionEffectType.UNLUCK).getAmplifier());
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 0.85F, 0.25F);
        }
    }
}

