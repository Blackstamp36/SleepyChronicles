package org.blackstamp.sleepyChronicles.item.nullItems;

import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class nullItems {

    public ItemStack createNullItem(Material material, String name) {
        Random r = new Random();
        int drop = r.nextInt(1,4);

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of("null"));

        List<String> lore = new ArrayList<>();
        lore.add("§8§l—");
        lore.add("§e※ §8null");

        meta.setDisplayName(ChatColor.of("#db1fdb") + name);
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);
        item.setAmount(drop);

        return item;
    }
}
