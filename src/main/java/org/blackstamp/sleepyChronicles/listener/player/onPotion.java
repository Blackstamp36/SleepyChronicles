package org.blackstamp.sleepyChronicles.listener.player;

import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffectType;

@Registrable
public class onPotion implements Listener {

    @EventHandler
    private void onPotion(EntityPotionEffectEvent e){
        Entity entity = e.getEntity();

        if(entity instanceof Player p){
            if(e.getNewEffect() != null) {
                if (e.getNewEffect().getType().equals(PotionEffectType.WEAVING)) {

                    p.sendActionBar("§7You're starting to feel invisible..");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.75F, 1.5F);
                }
            } else if(e.getOldEffect() != null){
                if(e.getOldEffect().getType().equals(PotionEffectType.WEAVING)){
                    p.sendActionBar("§7Now you're visible to the others!");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,0.75F,0.25F);

                }
            }
        }
    }
}
