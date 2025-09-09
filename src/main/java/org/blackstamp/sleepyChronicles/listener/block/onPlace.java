package org.blackstamp.sleepyChronicles.listener.block;

import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffectType;

@Registrable
public class onPlace implements Listener {

    @EventHandler
    private void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();

        if(p.hasPotionEffect(PotionEffectType.WEAVING)){
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT,0.75F,0.25F);
            e.setCancelled(true);

        }
    }

}
