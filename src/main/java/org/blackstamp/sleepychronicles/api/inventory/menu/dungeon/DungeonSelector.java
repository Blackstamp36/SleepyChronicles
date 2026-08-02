package org.blackstamp.sleepychronicles.api.inventory.menu.dungeon;

import org.blackstamp.sleepychronicles.api.chat.ChatManager;
import org.blackstamp.sleepychronicles.api.dungeon.DungeonType;
import org.blackstamp.sleepychronicles.api.dungeon.RunManager;
import org.blackstamp.sleepychronicles.api.inventory.MenuItems;
import org.blackstamp.sleepychronicles.api.inventory.MenuTemplate;
import org.blackstamp.sleepychronicles.api.item.ItemBuilder;
import org.blackstamp.sleepychronicles.api.party.PartyManager;
import org.blackstamp.sleepychronicles.api.party.SleepyParty;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class DungeonSelector extends MenuTemplate {

    public DungeonSelector(Player p, String owner){
        super(p,owner,"dungeon_selector",54);
    }

    @Override
    public void initInventory(){
        super.fill(MenuItems.HOLLOW.build());

        int slot = 10; // Starting slot.

        for(DungeonType type : DungeonType.values()){
            super.inventory.setItem(slot, type.build());
            slot += 2;

            if(slot > super.inventory.getSize()) break;
        }
    }

    @Override
    public void click(InventoryClickEvent e){
        Inventory clicked = e.getClickedInventory();

        if(clicked != super.getInventory()) return;

        e.setCancelled(true);

        final ItemStack currentItem = e.getCurrentItem();
        if(currentItem == null || currentItem.getType().isAir()) return;

        final ItemBuilder clickedItem = new ItemBuilder(currentItem);
        String id = clickedItem.getID();

        if(id == null || MenuItems.isMenuItem(id)) return;
        if(currentItem.getType().isAir() || !currentItem.hasItemMeta()) return;

        DungeonType dungeon = DungeonType.getDungeon(id);
        if(dungeon == null) return;

        p.closeInventory(); // Essential so the user can see the messages below.

        UUID uuid = p.getUniqueId();

        if(!PartyManager.hasParty(uuid)){
            ChatManager.sendMessage(p, true,"You aren't in a party! (/p create)");
            return;
        }

        SleepyParty party = PartyManager.getParty(uuid);

        if(!PartyManager.isLeader(uuid,party)){
            ChatManager.sendMessage(p, true,"Only the leader may begin the dungeon.");
            return;
        }

        RunManager.createRun(party,dungeon);
    }
}
