package org.blackstamp.sleepychronicles.api.inventory;

import org.blackstamp.sleepychronicles.api.item.templates.BaseItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Supplier;

public enum MenuItems {
    BLANK(() -> new BaseItem(Material.WHITE_STAINED_GLASS_PANE)
            .setDisplay("")
    ),
    HOLLOW(() -> new BaseItem(Material.BLACK_STAINED_GLASS_PANE)
            .setDisplay("")
    ),
    NEXT(() -> new BaseItem(Material.LIME_DYE)
            .setDisplay("<#43eb34>Next")
    ),
    PREVIOUS(() -> new BaseItem(Material.RED_DYE)
            .setDisplay("<#eb4034>Previous?")
    );

    private static final Map<String, MenuItems> MENU_ITEMS = new HashMap<>();

    static {
        for(MenuItems item : values()){ MENU_ITEMS.put(item.getID(),item); }
    }

    private final Supplier<BaseItem> template;

    MenuItems(Supplier<BaseItem> template){ this.template = template; }

    public ItemStack build(){ return template.get().setID(this.getID()).build(); }
    public String getID(){ return this.name().toLowerCase(); }

    public static boolean isMenuItem(String id){ return MENU_ITEMS.containsKey(id); }
}