package org.blackstamp.sleepychronicles.deprecated.listener.block.chest;

import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
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
        GlobalClass global = new GlobalClass();
        Player p = e.getPlayer();
        Block block = e.getClickedBlock();

        if(!p.isSneaking()) return;

        if(block != null) {
            if(block.getType().equals(Material.CHEST)){
                block.breakNaturally(true);

                block.getWorld().getEntities().stream()
                        .filter(Item.class::isInstance)
                        .filter(item -> item.getLocation().distance(block.getLocation()) <= 3)
                        .filter(item -> ((Item) item).getItemStack().getType().equals(Material.CHEST))
                        .forEach(Entity::remove);

                p.playSound(p, Sound.BLOCK_NOTE_BLOCK_CHIME, 0.5F, 1.5F);
                p.playSound(p, Sound.BLOCK_CHEST_OPEN, 0.85F, 0.5F);
                global.spawnParticles(block.getLocation(), Particle.WAX_ON, null, 50);
                global.spawnParticles(block.getLocation(), Particle.GUST, null, 1);
                global.spawnParticles(block.getLocation(), Particle.ENCHANTED_HIT, null, 5);

            }
        }
    }
}
