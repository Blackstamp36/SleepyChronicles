package org.blackstamp.sleepychronicles.global.utils.manager;

import org.blackstamp.sleepychronicles.deprecated.loot_table.lootTable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ChestManager {
    LootManager lM = new LootManager();

    public void placeLootChest(Location l, lootTable table){
        l.getBlock().setType(Material.CHEST);
        List<ItemStack> loot = lM.getRandomLoot(table);

        Chest chest = (Chest) l.getBlock().getState();
        Inventory inv = chest.getInventory();
        inv.clear();

        List<Integer> slots = new ArrayList<>(IntStream.range(0, inv.getSize()).boxed().toList());

        int currentSlot = 0;

        for(ItemStack item : loot){
            if(item == null) continue;

            currentSlot+= ThreadLocalRandom.current().nextInt(2,6);

            if(currentSlot >= slots.size()) break;

            ItemStack currentItem = inv.getItem(currentSlot);

            if(currentItem != null) continue;

            inv.setItem(currentSlot, item);
        }
    }
}
