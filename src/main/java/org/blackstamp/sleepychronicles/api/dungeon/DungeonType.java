package org.blackstamp.sleepychronicles.api.dungeon;

import lombok.Getter;
import net.kyori.adventure.text.format.NamedTextColor;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.api.constant.SleepyIcons;
import org.blackstamp.sleepychronicles.api.item.SleepyItem;
import org.blackstamp.sleepychronicles.api.mobs.boss.SleepyBosses;
import org.blackstamp.sleepychronicles.api.mobs.config.DungeonConfig;
import org.blackstamp.sleepychronicles.game.items.ItemFamily;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public enum DungeonType {
    TEST(DungeonConfig.builder()
            .bossId(SleepyBosses.DARKNESS_EMPEROR.getId())
            .schematic("")
            .maxPlayers(4)
            .radius(50.0D)
            .timeLimitSeconds(1800)
            .icon(() -> new SleepyItem(Material.GRASS_BLOCK, ItemFamily.TRINKETS)
                    .setDisplay("Test", SleepyPalette.VANILLA)
                    .setLore("Hello! I'm a test!", null)
                    .setIcon(SleepyIcons.PERSONS_ICON, null)
            )
            .build()
    );

    @Getter private final DungeonConfig config;

    private static final Map<String, DungeonType> DUNGEON_MAP = new HashMap<>();

    static {
        for(DungeonType dungeon : values()){ DUNGEON_MAP.put(dungeon.getID(), dungeon); }
    }

    DungeonType(DungeonConfig config){
        this.config = config;
    }

    public ItemStack build(){
        return config.icon().get()
                .setID(this.getID())
                .addLore("Max: " + config.maxPlayers(), NamedTextColor.GOLD,false)
                .build();
    }
    public String getID(){ return this.name().toLowerCase(); }

    public static DungeonType getDungeon(String id){ return DUNGEON_MAP.get(id); }
}