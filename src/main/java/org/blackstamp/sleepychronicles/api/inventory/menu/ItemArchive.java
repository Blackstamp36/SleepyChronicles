package org.blackstamp.sleepychronicles.api.inventory.menu;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.api.inventory.MenuItems;
import org.blackstamp.sleepychronicles.api.inventory.MenuTemplate;
import org.blackstamp.sleepychronicles.api.item.ItemManager;
import org.blackstamp.sleepychronicles.api.item.SleepyItems;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemArchive extends MenuTemplate {
    private final int blankSlots = 28;
    final ArrayList<SleepyItems> stackList = SleepyItems.getList();
    private int page = 0;
    private final int totalPages = (int) Math.ceil((double) stackList.size() / blankSlots);

    public ItemArchive(Player p){ super(p, p.getName(), "items", 54); }

    @Override
    public void initInventory(){
        super.empty();

        final ArrayList<SleepyItems> stackList = SleepyItems.getList();

        super.setOutline(MenuItems.HOLLOW.build());
        super.setItem(MenuItems.PREVIOUS.build(), 45);
        super.setItem(MenuItems.NEXT.build(), 53);

        for(int i = 0; i < blankSlots; i++){
            final int index = (blankSlots * page) + i;

            if(index >= stackList.size()) break;

            final SleepyItems item = stackList.get(index);
            super.addItem(item.build());
        }
    }

    @EventHandler
    public void click(InventoryClickEvent e){
        Inventory clicked = e.getClickedInventory();

        if(clicked != super.inventory) return;
        e.setCancelled(true);

        final ItemStack currentItem = e.getCurrentItem();
        final ItemManager clickedItem = new ItemManager(currentItem);

        if(currentItem == null || currentItem.getType().isAir() || !currentItem.hasItemMeta()) return;
        if(clickedItem.getID().equals(MenuItems.HOLLOW.getID()) || clickedItem.getID() == null) return;

        switch(clickedItem.getID()){
            case "next" -> {
                if(this.page >= totalPages - 1) return;
                this.page++;
                this.initInventory();
                p.playSound(Sound.sound(Key.key("ui.book.page_turn"), Sound.Source.MASTER, 1.0F, 1.0F));
            }
            case "previous" -> {
                if(this.page <= 0) return;
                this.page--;
            }
            default -> {
                p.sendMessage("Retrieving item..");
                p.getInventory().addItem(clickedItem.build());
                p.playSound(Sound.sound(Key.key("ui.loom.select_pattern"), Sound.Source.MASTER, 1.0F, 1.25F));
            }
        }
    }

    @EventHandler
    public void open(InventoryOpenEvent e){
        if(e.getInventory() != super.inventory) return;
        p.playSound(Sound.sound(Key.key("ui.cartography_table.take_result"), Sound.Source.MASTER, 1.0F, 0.75F));
    }

    @EventHandler
    public void close(InventoryCloseEvent e){ // todo: check event functionality
        if(e.getInventory() != super.inventory) return;
        HandlerList.unregisterAll(this);
        p.playSound(Sound.sound(Key.key("block.chest.close"), Sound.Source.MASTER, 1.0F, 0.75F));
    }
}
