package org.blackstamp.sleepychronicles.api.item;

import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.api.constant.SleepyIcons;
import org.blackstamp.sleepychronicles.api.item.trinket.TrinketAbility;
import org.blackstamp.sleepychronicles.game.items.ItemFamily;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public enum SleepyItems{
    NULL_POWDER(() -> new SleepyItem(Material.GUNPOWDER, SleepyPalette.NULL)
            .setDisplay("Null-powder")
            .setGlint(true),
            null,
            null
    ),
    BOB_FLESH(() -> new SleepyItem(Material.ROTTEN_FLESH, SleepyPalette.BOB)
            .setDisplay("Bob's Flesh")
            .setGlint(true),
            null,
            null
    ),
    MEMENTO_MORI(() -> new SleepyItem(Material.SADDLE, SleepyPalette.TRINKET)
            .setDisplay("Memento-mori")
            .setLore("Winner takes it all!", BasicPalette.GRAY.tag(true))
            .setIcon(SleepyIcons.TRINKET_ICON, BasicPalette.GREEN.tag(true))
            .setFamily(ItemFamily.TRINKETS)
            .setGlint(true),
            null,
            null
    );

    private final ItemAbility ability;
    private final TrinketAbility trinketAbility;
    private final Supplier<SleepyItem> template;

    private static final Map<String, ItemAbility> ABILITY_MAP = new HashMap<>();
    private static final Map<String, TrinketAbility> TRINKET_ABILITY_MAP = new HashMap<>();

    static {
        for(SleepyItems item : values()){
            if(item.ability != null) ABILITY_MAP.put(item.getID(),item.ability);
            if(item.trinketAbility != null) TRINKET_ABILITY_MAP.put(item.getID(),item.trinketAbility);
        }
    }

    SleepyItems(Supplier<SleepyItem> template, @Nullable ItemAbility ability, @Nullable TrinketAbility trinketAbility){
        this.template = template;
        this.ability = ability;
        this.trinketAbility = trinketAbility;
    }

    public static @NotNull ArrayList<SleepyItems> getList(){ return new ArrayList<>(List.of(SleepyItems.values())); }
    public String getID(){ return this.name().toLowerCase(); }
    public ItemStack build(){ return template.get().setID(this.getID()).build(); }

    public static ItemAbility getAbility(String id){ return ABILITY_MAP.get(id); }
    public static TrinketAbility getTrinketAbility(String id){ return TRINKET_ABILITY_MAP.get(id); }
}
