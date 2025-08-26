package org.blackstamp.sleepyChronicles.item.drops;

import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.ArrayList;
import java.util.List;

public class witherDrops {

    public ItemStack createBlindSoul(){
        ItemStack item = new ItemStack(Material.RED_DYE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("blind_soul")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l—");
        lore.add("§e※ §8Dropped by Mechanical Eye.");
        meta.setDisplayName(ChatColor.of("#4a89a5") + "Blind Soul");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }
}

