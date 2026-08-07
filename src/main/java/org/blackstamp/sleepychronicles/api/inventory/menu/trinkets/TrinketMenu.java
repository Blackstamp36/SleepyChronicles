package org.blackstamp.sleepychronicles.api.inventory.menu.trinkets;

import org.blackstamp.sleepychronicles.api.data.base64.Base64Utils;
import org.blackstamp.sleepychronicles.api.inventory.MenuItems;
import org.blackstamp.sleepychronicles.api.inventory.MenuTemplate;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TrinketMenu extends MenuTemplate {

    public TrinketMenu(Player p, String owner) {
        super(p, owner, "trinkets_menu", 27);
    }

    public void initInventory() {
        super.empty();
        String trinketData = this.get();

        super.fill(MenuItems.BLANK.build());
        for(int slot : getTrinketSlots()) super.setItems(ItemStack.of(Material.AIR), slot);

        if(trinketData == null || trinketData.isEmpty()) return;

        ItemStack[] trinketInv = (ItemStack[]) Base64Utils.fromBase64(trinketData);

        for(int i = 0; i < getTrinketSlots().length; i++) {
            ItemStack trinket = trinketInv[i];
            final int currentSlot = getTrinketSlots()[i];

            super.inventory.setItem(currentSlot, trinket);
        }
    }

    public void click(InventoryClickEvent e) {
        Inventory clickedInventory = e.getClickedInventory();

        if(clickedInventory != getInventory() || clickedInventory == null) return;


    }
}
