package org.blackstamp.sleepychronicles.api.item;

import net.kyori.adventure.text.format.TextColor;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.game.items.ItemFamily;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public class SleepyItem extends ItemBuilder<SleepyItem> {

    public SleepyItem(Material material, @NotNull ItemFamily family) {
        this(material);
        super.setFamily(family);
    }

    public SleepyItem(Material material){
        super(material);
    }

    public SleepyItem setIcon(char value, TextColor textColor) {
        return this.self().addLore("[" + value + "]", textColor, true);
    }
}