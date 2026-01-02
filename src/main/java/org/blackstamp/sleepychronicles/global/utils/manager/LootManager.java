package org.blackstamp.sleepychronicles.global.utils.manager;

import com.google.gson.Gson;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.deprecated.loot_table.lootCount;
import org.blackstamp.sleepychronicles.deprecated.loot_table.lootEntry;
import org.blackstamp.sleepychronicles.deprecated.loot_table.lootPool;
import org.blackstamp.sleepychronicles.deprecated.loot_table.lootTable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LootManager {
    private final Random r = new Random();

    public lootTable getLootTable(String lootTableFile){
        try(InputStream stream = SleepyChronicles.getInstance().getResource("loot_table/" + lootTableFile + ".json");
            InputStreamReader reader = new InputStreamReader(stream)){

            Gson gson = new Gson();
            return gson.fromJson(reader, lootTable.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        SleepyChronicles.getInstance().getLogger().warning("Loot table doesn't exist: " + lootTableFile);
        return null;
    }

    public List<ItemStack> getRandomLoot(lootTable table){
        List<ItemStack> loot = new ArrayList<>();

        for(lootPool pool : table.getPools()){
            for(int i = 0; i < pool.getRolls(); i++){
                lootEntry chosen = getRandomEntry(pool.getEntries());

                if(chosen == null) continue;
                if(r.nextDouble() > chosen.getChance()) continue;

                lootCount count = chosen.getCount();

                int amount = r.nextInt(count.getMax() - count.getMin() + 1) + count.getMin();
                Material material = Material.matchMaterial(chosen.getMaterial());

                if(material == null) continue;

                loot.add(new ItemStack(material, amount));
                Collections.shuffle(loot, r);
            }
        }

        return loot;
    }

    private lootEntry getRandomEntry(List<lootEntry> entries){
        int totalWeight = entries.stream().mapToInt(lootEntry::getWeight).sum();
        int roll = r.nextInt(totalWeight);
        int current = 0;

        for(lootEntry entry : entries){
            current += entry.getWeight();

            if(roll < current) return entry;
        }

        return null;
    }
}
