package org.blackstamp.sleepychronicles.api.item;

import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.game.items.ItemFamily;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public class SleepyItem extends ItemBuilder {
    SleepyPalette palette;

    public SleepyItem(Material material, @NotNull ItemFamily family){
        this(material, family.getPalette());
        super.setFamily(family);
    }

    public SleepyItem(Material material, @NotNull SleepyPalette palette){
        super(material);

        this.palette = palette;
    }

    public SleepyItem setDisplay(String display, int colorType){
        super.setDisplay(display);
        return this;
    }

    public SleepyItem setAmount(int value){
        super.setAmount(value);
        return this;
    }

    public SleepyItem addLore(String value, String textColor, boolean newLine){
        super.addLore(value, textColor, newLine);
        return this;
    }

    public SleepyItem setLore(String value, String color){
        super.setLore(value, color);
        return this;
    }

    public SleepyItem setIcon(char value, String color){
        addLore("[" + value + "]", color, true);
        return this;
    }

    public SleepyItem setID(String value){
        super.setID(value);
        return this;
    }

    public SleepyItem setFamily(ItemFamily family){
        super.setFamily(family);
        return this;
    }

    public SleepyItem setGlint(boolean value){
        super.setGlint(value);
        return this;
    }
}