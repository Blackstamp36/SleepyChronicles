package org.blackstamp.sleepychronicles.api.inventory.menu.dungeon;

import org.blackstamp.sleepychronicles.api.inventory.MenuTemplate;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class DungeonSelector extends MenuTemplate {

    public DungeonSelector(Player p, String owner){
        super(p,owner,"",54);
    }

    @Override
    public void initInventory() {

    }

    @Override
    public void click(InventoryClickEvent e) {

    }
}
