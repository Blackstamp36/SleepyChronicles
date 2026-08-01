package org.blackstamp.sleepychronicles.api.dungeon;

import lombok.Getter;
import org.blackstamp.sleepychronicles.api.color.BasicPalette;
import org.blackstamp.sleepychronicles.api.item.ItemBuilder;
import org.blackstamp.sleepychronicles.api.item.SleepyItem;
import org.blackstamp.sleepychronicles.game.items.ItemFamily;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public enum DungeonType {
    TEST(() -> new SleepyItem(Material.GRASS_BLOCK, ItemFamily.TRINKETS)
            .setDisplay("Test")
            .setLore("Hello! I'm a test!", null),
            4,
            100
    );

    private final Supplier<ItemBuilder> template;
    @Getter private final int maxSize;
    @Getter private final double radius;

    private static final Map<String, DungeonType> DUNGEON_MAP = new HashMap<>();

    static {
        for(DungeonType dungeon : values()){ DUNGEON_MAP.put(dungeon.getID(), dungeon); }
    }

    DungeonType(Supplier<ItemBuilder> template, int maxSize, double radius){
        this.template = template;
        this.maxSize = maxSize;
        this.radius = radius;
    }

    public ItemStack build(){
        return template.get()
                .setID(this.getID())
                .addLore("Max: " + maxSize, BasicPalette.GOLD.getColor(),true)
                .build();
    }
    public String getID(){ return this.name().toLowerCase(); }

    public static DungeonType getDungeon(String id){ return DUNGEON_MAP.get(id); }
}