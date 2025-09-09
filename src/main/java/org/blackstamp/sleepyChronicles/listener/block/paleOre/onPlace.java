package org.blackstamp.sleepyChronicles.listener.block.paleOre;

import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

@Registrable
public class onPlace implements Listener {

    @EventHandler
    private void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();

        if (b.getType().equals(Material.CHISELED_RESIN_BRICKS) && p.getGameMode().equals(GameMode.SURVIVAL)) {
            e.setCancelled(true);
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT,0.75F,0.25F);

        }
    }
}
