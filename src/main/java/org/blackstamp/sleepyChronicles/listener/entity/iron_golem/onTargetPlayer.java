package org.blackstamp.sleepyChronicles.listener.entity.iron_golem;

import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Registrable
public class onTargetPlayer implements Listener {

    @EventHandler
    private void onTrack(EntityTargetEvent e){
        Entity entity = e.getEntity();
        if(e.getTarget() instanceof Player p){

            if(entity.getScoreboardTags().contains("quantumGolem")){
                p.playSound(p.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE,1,0.25F);

            }
        }

    }
}
