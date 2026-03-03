package org.blackstamp.sleepychronicles.global.utils.data;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BlockData implements Listener {
    private final Map<Location, UUID> playerPlacedBlocks = new HashMap<>();

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Block block = e.getBlockPlaced();
        Player p = e.getPlayer();
        playerPlacedBlocks.put(block.getLocation(), p.getUniqueId());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        UUID placerUUID = playerPlacedBlocks.get(block.getLocation());

        if (placerUUID != null) {
            e.getPlayer().sendMessage("This block was placed by: " + placerUUID);
            playerPlacedBlocks.remove(block.getLocation());
        } else {
            e.getPlayer().sendMessage("This is a natural block!");
        }
    }

    public boolean wasPlacedByPlayer(Block block) {
        return playerPlacedBlocks.containsKey(block.getLocation());
    }

    public UUID getPlacerUUID(Block block) {
        return playerPlacedBlocks.get(block.getLocation());
    }

    public void cleanup() {
        playerPlacedBlocks.entrySet().removeIf(entry -> {
            Location loc = entry.getKey();
            return loc.getWorld().getBlockAt(loc).getType().isAir();
        });
    }
}

