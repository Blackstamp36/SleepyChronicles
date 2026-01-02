package org.blackstamp.sleepychronicles.deprecated.loot_table;

import lombok.Getter;

import java.util.List;

public class lootPool {
    @Getter
    private int rolls;
    @Getter
    private List<lootEntry> entries;

}
