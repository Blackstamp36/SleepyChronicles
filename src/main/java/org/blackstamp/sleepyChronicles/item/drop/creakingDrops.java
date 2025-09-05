package org.blackstamp.sleepyChronicles.item.drop;

import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class creakingDrops {

    public ItemStack createBobFlesh(){
        Random r = new Random();
        int drop = r.nextInt(1,6);

        ItemStack item = new ItemStack(Material.ROTTEN_FLESH);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("bob_flesh")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l—");
        lore.add("§e※ §8Wood, or what's left of it.");
        meta.setDisplayName(ChatColor.of("#ada19f") + "Bob's Flesh");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);
        item.setAmount(drop);

        return item;
    }
}
