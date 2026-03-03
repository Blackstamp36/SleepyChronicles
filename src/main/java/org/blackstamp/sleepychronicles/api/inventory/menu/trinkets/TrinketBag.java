package org.blackstamp.sleepychronicles.api.inventory.menu.trinkets;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.blackstamp.sleepychronicles.api.chat.ChatUtils;
import org.blackstamp.sleepychronicles.api.data.base64.Base64Utils;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.api.inventory.MenuItems;
import org.blackstamp.sleepychronicles.api.inventory.MenuTemplate;
import org.blackstamp.sleepychronicles.api.item.ItemManager;
import org.blackstamp.sleepychronicles.game.items.ItemFamily;
import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TrinketBag extends MenuTemplate {
    public TrinketBag(Player p, String owner){
        super(p, owner, "trinkets", 27);
    }

    @Override
    public void initInventory(){
        super.empty();
        String trinketData = get();

        super.fill(MenuItems.BLANK.build());
        for(int slot : getTrinketSlots()) super.setItems(ItemStack.of(Material.AIR), slot);

        if(trinketData == null || trinketData.isEmpty()) return;

        ItemStack[] trinketInv = (ItemStack[]) Base64Utils.fromBase64(trinketData);

        for(int i = 0; i < getTrinketSlots().length; i++){
            ItemStack trinket = trinketInv[i];
            final int currentSlot = getTrinketSlots()[i];

            super.inventory.setItem(currentSlot, trinket);
        }
    }

    @EventHandler
    public void click(InventoryClickEvent e){
        Inventory clicked = e.getClickedInventory();

        if(clicked != getInventory() || clicked == null) return;

        ItemStack cursorStack = e.getCursor() != null ? e.getCursor() : new ItemStack(Material.AIR);
        ItemStack currentStack = e.getCurrentItem() != null ? e.getCurrentItem() : new ItemStack(Material.AIR);

        final ItemManager cursorItem = new ItemManager(cursorStack);
        final ItemManager currentItem = new ItemManager(currentStack);

        if(isForbidden(cursorItem) || isForbidden(currentItem)) e.setCancelled(true);
    }

    private boolean isForbidden(@NotNull ItemManager manager){
        if(manager.build().getType().equals(Material.AIR)) return false;
        if(manager.hasID() && manager.getID().equals(MenuItems.BLANK.getID())) return true;
        if(!manager.hasFamily()) return true;

        return !manager.getFamily().equals(ItemFamily.TRINKETS.getName());
    }

    @EventHandler
    public void open(InventoryOpenEvent e){
        if(e.getInventory() != super.inventory) return;
        ChatUtils.sendMessage(p, "Showing trinkets!");
        p.playSound(Sound.sound(Key.key("ui.cartography_table.take_result"), Sound.Source.MASTER, 1.0F, 0.75F));
    }

    @EventHandler
    public void close(InventoryCloseEvent e){
        if(e.getInventory() != super.inventory) return;
        HandlerList.unregisterAll(this);
        save();
        ChatUtils.sendMessage(p, "Trinkets saved!");
        p.playSound(Sound.sound(Key.key("block.ender_chest.close"), Sound.Source.MASTER, 1.0F, 0.75F));
    }

    private void save(){
        ItemStack[] savedTrinkets = new ItemStack[getTrinketSlots().length];

        for(int i = 0; i < savedTrinkets.length ; i++){
            final int currentSlot = getTrinketSlots()[i];

            savedTrinkets[i] = super.inventory.getItem(currentSlot);
        }

        String trinketData = Base64Utils.toBase64(savedTrinkets);
        PersistentData.set(super.p, SleepyKeys.TRINKETS_INV, PersistentDataType.STRING, trinketData);
    }

    @Nullable
    private String get(){
        return PersistentData.get(super.p, SleepyKeys.TRINKETS_INV, PersistentDataType.STRING);
    }

    public static boolean hasTrinket(Player p, String value){
        String data = PersistentData.get(p, SleepyKeys.TRINKETS_INV, PersistentDataType.STRING);
        ItemStack[] trinkets = (ItemStack[]) Base64Utils.fromBase64(data);

        for(ItemStack slot : trinkets) {
            if(slot == null) continue;

            ItemManager manager = new ItemManager(slot);

            if(!manager.hasID()) continue;

            return manager.getID().equals(value);
        }

        return false;
    }

    private int[] getTrinketSlots(){ return new int[]{4, 12, 14, 22}; }
}