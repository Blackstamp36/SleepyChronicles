package org.blackstamp.sleepychronicles.api.item;

import co.aikar.commands.annotation.Optional;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.game.items.ItemFamily;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public class SleepyItem extends ItemManager {
    String color1;
    String color2;
    String color3;

    public SleepyItem(Material material, @NotNull ItemFamily family){
        this(material, family.getPalette());
        super.setFamily(family);
    }

    public SleepyItem(Material material, @NotNull SleepyPalette palette){
        this(material, palette.getColor1(), palette.getColor2(), palette.getColor3());
    }

    public SleepyItem(Material material, @Optional String... colors){
        super(material);
        if(colors[0] != null) color1 = "<" + colors[0] + ">";
        if(colors[1] != null) color2 = "<" + colors[1] + ">";
        if(colors[2] != null) color3 = "<" + colors[2] + ">";
    }

    public SleepyItem setDisplay(String display){
        super.setDisplay(color1 + display);
        return this;
    }

    public SleepyItem setAmount(int value){
        super.setAmount(value);
        return this;
    }

    public SleepyItem addLore(String value, String color, boolean extra){
        super.addLore(value, color, extra);

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
