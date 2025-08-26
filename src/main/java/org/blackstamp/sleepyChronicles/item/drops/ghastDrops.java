package org.blackstamp.sleepyChronicles.item.drops;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.ArrayList;
import java.util.List;

public class ghastDrops {

    public ItemStack createBloodTear(){
        ItemStack item = new ItemStack(Material.GHAST_TEAR);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("blood_tear")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l—");
        lore.add("§e※ §8You're miserable.");
        meta.setDisplayName(net.md_5.bungee.api.ChatColor.of("#8f594d") + "Blood Tear");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }
}
