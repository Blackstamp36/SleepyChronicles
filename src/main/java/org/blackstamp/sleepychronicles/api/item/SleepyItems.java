package org.blackstamp.sleepychronicles.api.item;

import net.kyori.adventure.text.format.NamedTextColor;
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
    NULL_POWDER(() -> new SleepyItem(Material.GUNPOWDER)
            .setDisplay("Null-powder", SleepyPalette.NULL)
            .setGlint(true),
            null,
            null
    ),
    BOB_FLESH(() -> new SleepyItem(Material.ROTTEN_FLESH)
            .setDisplay("Bob's Flesh", SleepyPalette.BOB)
            .setGlint(true),
            null,
            null
    ),
    MEMENTO_MORI(() -> new SleepyItem(Material.SADDLE)
            .setDisplay("Memento-mori", SleepyPalette.TRINKET)
            .setLore("Winner takes it all!", NamedTextColor.GRAY)
            .setIcon(SleepyIcons.TRINKET_ICON, NamedTextColor.GREEN)
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
        for(SleepyItems item : values()) {
            if(item.ability != null) {
                ABILITY_MAP.put(item.getID(), item.ability);
            }
            if(item.trinketAbility != null) {
                TRINKET_ABILITY_MAP.put(item.getID(),item.trinketAbility);
            }
        }
    }

    SleepyItems(Supplier<SleepyItem> template, @Nullable ItemAbility itemAbility, @Nullable TrinketAbility trinketAbility) {
        this.template = template;
        this.ability = itemAbility;
        this.trinketAbility = trinketAbility;
    }

    public static @NotNull List<SleepyItems> getList() {
        return new ArrayList<>(List.of(SleepyItems.values()));
    }
    public String getID() {
        return this.name().toLowerCase();
    }
    public ItemStack build(){
        return template.get().setID(this.getID()).build();
    }

    public static ItemAbility getAbility(String id) {
        return ABILITY_MAP.get(id);
    }
    public static TrinketAbility getTrinketAbility(String id) {
        return TRINKET_ABILITY_MAP.get(id);
    }
}
