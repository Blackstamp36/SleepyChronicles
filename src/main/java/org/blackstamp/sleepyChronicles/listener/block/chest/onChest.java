package org.blackstamp.sleepyChronicles.listener.block.chest;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.ChatFormatter;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

@Registrable
public class onChest implements Listener {

    @EventHandler
    public void onChest(PlayerInteractEvent e) {
        globalClass global = new globalClass();
        Player p = e.getPlayer();
        Block block = e.getClickedBlock();

        if (block != null) {
            if (block.getType().equals(Material.CHEST)) {
                block.breakNaturally(true);

                block.getWorld().getEntities().stream()
                        .filter(Item.class::isInstance)
                        .filter(item -> item.getLocation().distance(block.getLocation()) <= 3)
                        .filter(item -> ((Item) item).getItemStack().getType().equals(Material.CHEST))
                        .forEach(Entity::remove);

                p.playSound(p, Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 1.5F);
                p.playSound(p, Sound.BLOCK_CHEST_OPEN, 1, 0.5F);
                global.spawnParticles(block.getLocation(), Particle.WAX_ON, null, 50);
                global.spawnParticles(block.getLocation(), Particle.GUST, null, 1);
                global.spawnParticles(block.getLocation(), Particle.HEART, null, 5);
                p.sendTitle("", ChatFormatter.format("&e• &6Treasure found! &e•"), 10, 20, 5);

            }
        }
    }
}
