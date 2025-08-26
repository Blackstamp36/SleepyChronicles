package org.blackstamp.sleepyChronicles.util.data;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class playerData {
    @Setter
    @Getter
    private int totems;
    private List<ItemStack> trinketsInventory;

    public playerData() {
        this.totems = 0;
        this.trinketsInventory = new ArrayList<>();
    }

    public List<ItemStack> getTrinkets() {
        return trinketsInventory;
    }
    public void setTrinkets(List<ItemStack> perksInventory) {
        this.trinketsInventory = perksInventory;
    }

    public void setTrinketsFromInventory(Inventory inventory) {
        this.trinketsInventory = new ArrayList<>();
        int[] allowedSlots = {4, 12, 14, 22};

        for (int slot : allowedSlots) {
            ItemStack item = inventory.getItem(slot);
            if (item != null && !isDecoration(item)) {
                this.trinketsInventory.add(item.clone());
            }
        }
    }

    public Inventory getTrinketsAsInventory(Player player) {
        Inventory inv = getTrinketsInitialContents();

        if (trinketsInventory != null) {
            int[] allowedSlots = {4, 12, 14, 22};

            for (int i = 0; i < Math.min(trinketsInventory.size(), allowedSlots.length); i++) {
                ItemStack item = trinketsInventory.get(i);
                if (item != null) {
                    inv.setItem(allowedSlots[i], item);
                }
            }
        }
        return inv;
    }

    public boolean isDecoration(ItemStack item) {
        if (item == null) return false;
        if (item.getType() != Material.BLACK_STAINED_GLASS_PANE) return false;

        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();

            return meta.hasDisplayName() && meta.getDisplayName().equals("　");
        }
        return false;
    }

    private Inventory getTrinketsInitialContents(){
        Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST, "§dTRINKETS");
        ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = blackGlass.getItemMeta();
        meta.setDisplayName("　");
        blackGlass.setItemMeta(meta);
        for(int i = 0; i < 27; i++) {
            if(i == 4 || i == 12 || i == 14 || i == 22) {
                inv.setItem(i, new ItemStack(Material.AIR));
            } else {
                inv.setItem(i, blackGlass);
            }
        }

        return inv;
    }
}
