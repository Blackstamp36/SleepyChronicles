package org.blackstamp.sleepyChronicles.listener.entity;

import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.potion.PotionEffectType;

@Registrable
public class onTargetPlayer implements Listener {

    @EventHandler
    private void onTrack(EntityTargetEvent e){
        if(e.getTarget() instanceof Player p){

            if(p.hasPotionEffect(PotionEffectType.WEAVING)) e.setCancelled(true);
        }
    }
}
