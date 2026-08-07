package org.blackstamp.sleepychronicles.api.item.templates;

import net.kyori.adventure.text.format.TextColor;
import org.blackstamp.sleepychronicles.game.items.ItemFamily;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public class SleepyItem extends BaseItem<SleepyItem> {

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