package org.blackstamp.sleepychronicles.api.item;

import org.blackstamp.sleepychronicles.api.SleepyPalette;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public enum SleepyItems{
    NULL_POWDER(() -> (SleepyItem) new SleepyItem(Material.GUNPOWDER, SleepyPalette.NULL)
            .setDisplay("Null-powder")
            .setID("null_powder")
    );

    private final Supplier<SleepyItem> template;

    SleepyItems(Supplier<SleepyItem> template){ this.template = template; }

    public static @NotNull ArrayList<SleepyItems> getList(){ return new ArrayList<>(List.of(SleepyItems.values())); }
    public ItemStack build(){ return template.get().build(); }

}
