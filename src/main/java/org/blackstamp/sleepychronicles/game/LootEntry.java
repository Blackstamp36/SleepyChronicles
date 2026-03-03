package org.blackstamp.sleepychronicles.game;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;

public class LootEntry {
    final ItemStack drop;
    final int min;
    final int max;
    final double chance;

    public LootEntry(ItemStack drop, int min, int max, double chance){
        this.drop = drop;
        this.min = min;
        this.max = max;
        this.chance = chance;

        final double rand = ThreadLocalRandom.current().nextDouble();

        if(!(chance <= rand)) return;

        final int amount = ThreadLocalRandom.current().nextInt(min,max);

        for(int i = 0; i < amount; i++){ drop.add(1); }
    }

    @Nullable public ItemStack build(){ return drop; }
}