package org.blackstamp.sleepyChronicles.item.drops;

import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class witherDrops {

    public ItemStack createSoulOfVision(){
        Random r = new Random();
        int drop = r.nextInt(6,33);

        ItemStack item = new ItemStack(Material.RED_DYE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("soul_of_vision")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l—");
        lore.add("§e※ §8Dropped by Mechanical Eye.");
        meta.setDisplayName(ChatColor.of("#c26688") + "Soul of Vision");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);
        item.setAmount(drop);

        return item;
    }
}

