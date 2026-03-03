package org.blackstamp.sleepychronicles.deprecated.listener;

import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffectType;

@Registrable
public class onEat implements Listener {

    @EventHandler
    private void onEat(PlayerItemConsumeEvent e){
        Player p = e.getPlayer();

        if(p.hasPotionEffect(PotionEffectType.WIND_CHARGED)){
            p.sendActionBar(ChatColor.of("#af5220") + "You don't feel hungry..");
            p.playSound(p.getLocation(), Sound.ENTITY_ARMADILLO_STEP,0.5F,0.5F);
            p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK,0.25F,1F);
            e.setCancelled(true);
        }
    }
}
