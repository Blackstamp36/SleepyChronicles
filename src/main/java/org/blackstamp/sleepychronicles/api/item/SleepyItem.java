package org.blackstamp.sleepychronicles.api.item;

import co.aikar.commands.annotation.Optional;
import org.blackstamp.sleepychronicles.api.SleepyPalette;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.game.items.ItemFamily;
import org.bukkit.Material;
import org.bukkit.persistence.PersistentDataType;
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

    public SleepyItem setLore(String lore, boolean extra){
        if(extra) super.addLore("");
        super.setLore(lore);
        return this;
    }

    public SleepyItem setID(String value) {
        super.setID(value);
        return this;
    }
}
