package org.blackstamp.sleepychronicles.deprecated.loot_table;

import lombok.Getter;

public class lootEntry {
    @Getter
    private String material;
    @Getter
    private int weight;
    @Getter
    private lootCount count;
    @Getter
    private double chance;
}
