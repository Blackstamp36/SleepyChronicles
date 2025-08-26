package org.blackstamp.sleepyChronicles.item.drops;

import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class slimeDrops {

    public ItemStack createGhostSeed(){

        ItemStack item = new ItemStack(Material.ARMADILLO_SCUTE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("ghost_seed")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l—");
        lore.add("§e※ §8Baba.");
        meta.setDisplayName(ChatColor.of("#a16e45") + "Ghost's Seed");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }
}
