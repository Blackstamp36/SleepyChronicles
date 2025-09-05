package org.blackstamp.sleepyChronicles.item.drop;

import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.ArrayList;
import java.util.List;

public class foxDrops {

    public ItemStack createKitsuneTail() {

        ItemStack item = new ItemStack(Material.FEATHER);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of("kitsune_tail"));

        List<String> lore = new ArrayList<>();
        lore.add("§8§l—");
        lore.add("§e※ §8Mythical remains.");

        meta.setDisplayName(ChatColor.of("#e4ced1") + "Kitsune's Tail");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }
}
