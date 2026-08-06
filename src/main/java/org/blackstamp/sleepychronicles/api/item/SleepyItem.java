package org.blackstamp.sleepychronicles.api.item;

import net.kyori.adventure.text.format.TextColor;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.game.items.ItemFamily;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public class SleepyItem extends ItemBuilder {

    public SleepyItem(Material material, @NotNull ItemFamily family){
        this(material);
        super.setFamily(family);
    }

    public SleepyItem(Material material){
        super(material);
    }

    public SleepyItem setDisplay(String display, SleepyPalette palette){
        return this.setDisplay(display,palette,0);
    }

    public SleepyItem setDisplay(String display, SleepyPalette palette, int colorType){
        super.setDisplay(display,palette,colorType);
        return this;
    }

    public SleepyItem setAmount(int value){
        super.setAmount(value);
        return this;
    }

    public SleepyItem addLore(String value, TextColor textColor, boolean newLine){
        super.addLore(value, textColor, newLine);
        return this;
    }

    public SleepyItem setLore(String value, TextColor textColor){
        super.setLore(value, textColor);
        return this;
    }

    public SleepyItem setIcon(char value, TextColor textColor){
        return this.addLore("[" + value + "]", textColor, true);
    }

    @Override
    public SleepyItem setID(String value){
        super.setID(value);
        return this;
    }

    @Override
    public SleepyItem setFamily(ItemFamily family){
        super.setFamily(family);
        return this;
    }

    @Override
    public SleepyItem setGlint(boolean value){
        super.setGlint(value);
        return this;
    }
}