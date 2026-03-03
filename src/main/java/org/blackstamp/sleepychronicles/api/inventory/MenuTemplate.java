package org.blackstamp.sleepychronicles.api.inventory;

import lombok.Getter;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.api.constant.ConstantFields;
import org.blackstamp.sleepychronicles.api.item.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class MenuTemplate implements Listener, Cloneable {

    protected final Inventory inventory;
    protected final Player p;
    protected final String owner;

    public MenuTemplate(Player p, String owner, String name, int size) {
        this.inventory = Bukkit.createInventory(p, size, ConstantFields.MINI_MESSAGE.deserialize(name));
        this.p = p;
        this.owner = owner;

        this.initInventory();
        Bukkit.getServer().getPluginManager().registerEvents(this, SleepyChronicles.getInstance());
    }

    public void open(){ this.open(p); }
    public void open(@NotNull Player p){ p.openInventory(inventory); }

    public void close(){ this.close(p); }
    public void close(@NotNull Player p){ p.closeInventory(); }

    public void addItem(@NotNull ItemManager manager) { this.addItem(manager.build()); }
    public void addItem(@NotNull ItemStack item) { inventory.addItem(item); }

    public void setItem(@NotNull ItemManager manager, int slot) { this.setItem(manager.build(), slot); }
    public void setItem(@NotNull ItemStack item, int slot) { inventory.setItem(slot, item); }

    public void setItems(@NotNull ItemManager manager, int... slots){ setItems(manager.build(), slots); }
    public void setItems(@NotNull ItemStack item, int... slots){ for(int slot : slots) inventory.setItem(slot, item); }

    public void setRow(@NotNull ItemManager manager, int from, int to){ setRow(manager.build(), from, to); }
    public void setRow(@NotNull ItemStack item, int from, int to){ for(int i = from; i <= to; i++) inventory.setItem(i, item); }
    public void setRow(@NotNull Material material, int from, int to){ for(int i = from; i <= to; i++) inventory.setItem(i, ItemStack.of(material)); }

    public void setOutline(ItemManager manager) { setOutline(manager.build()); }
    public void setOutline(ItemStack item){
        final int size = inventory.getSize();
        final int rows = size / 9;

        for(int i = 0; i < 9; i++){
            inventory.setItem(i, item);
            inventory.setItem((size - 9) + i, item);
        }

        for(int row = 1; row < rows - 1; row++){
            inventory.setItem(row * 9, item);
            inventory.setItem(8 + (row * 9), item);
        }
    }

    public void fill(ItemManager manager) { fill(manager.build()); }
    public void fill(ItemStack item){
        final int size = inventory.getSize();

        for(int i = 0; i < size; i++) setItem(item, i);
    }

    public void empty(){ for(int i = 0; i < inventory.getSize(); i++) inventory.setItem(i, new ItemStack(Material.AIR)); }

    public abstract void initInventory();

    public void open(InventoryOpenEvent e){}
    public void close(InventoryCloseEvent e){}
    public abstract void click(InventoryClickEvent e);

    @Override
    public MenuTemplate clone() {
        try {
            return (MenuTemplate) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
