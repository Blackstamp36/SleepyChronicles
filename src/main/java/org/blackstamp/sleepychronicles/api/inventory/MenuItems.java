package org.blackstamp.sleepychronicles.api.inventory;

import org.blackstamp.sleepychronicles.api.item.ItemManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public enum MenuItems {
    BLANK(() -> new ItemManager(Material.WHITE_STAINED_GLASS_PANE).setDisplay("").setID("blank")),
    HOLLOW(() -> new ItemManager(Material.BLACK_STAINED_GLASS_PANE).setDisplay("").setID("hollow")),

    NEXT(() -> new ItemManager(Material.LIME_DYE).setDisplay("<#43eb34>Next").setID("next")),
    PREVIOUS(() -> new ItemManager(Material.RED_DYE).setDisplay("<#eb4034>Previous?").setID("previous"));

    private final Supplier<ItemManager> template;

    MenuItems(Supplier<ItemManager> template){ this.template = template; }

    public ItemStack build(){ return template.get().build(); }
    public String getID(){ return template.get().getID(); }
}