package org.blackstamp.sleepychronicles.api.item;

import org.blackstamp.sleepychronicles.api.constant.ConstantColors;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.api.constant.SleepyIcons;
import org.blackstamp.sleepychronicles.game.items.ItemFamily;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public enum SleepyItems{
    NULL_POWDER("null_powder", () -> new SleepyItem(Material.GUNPOWDER, SleepyPalette.NULL)
            .setDisplay("Null-powder")
            .setID("null_powder")
            .setGlint(true)
    ),
    BOB_FLESH("bob_flesh", () -> new SleepyItem(Material.ROTTEN_FLESH, SleepyPalette.BOB)
            .setDisplay("Bob's Flesh")
            .setID("bob_flesh")
            .setGlint(true)
    ),
    MEMENTO_MORI("memento_mori", () -> new SleepyItem(Material.SADDLE, SleepyPalette.TRINKET)
            .setDisplay("Memento-mori")
            .setLore("Winner takes it all!", ConstantColors.GRAY)
            .setIcon(SleepyIcons.TRINKET_ICON, ConstantColors.GREEN)
            .setFamily(ItemFamily.TRINKETS)
            .setGlint(true)
    );

    private final String id;
    private final Supplier<SleepyItem> template;

    SleepyItems(String id, Supplier<SleepyItem> template){
        this.id = id;
        this.template = template;
    }

    public static @NotNull ArrayList<SleepyItems> getList(){ return new ArrayList<>(List.of(SleepyItems.values())); }
    public String getID(){ return this.id; }
    public ItemStack build(){ return template.get().setID(this.id).build(); }
}
