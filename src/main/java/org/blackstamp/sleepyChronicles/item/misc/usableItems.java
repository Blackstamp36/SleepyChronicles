package org.blackstamp.sleepyChronicles.item.misc;

import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.ArrayList;
import java.util.List;

public class usableItems {

    public ItemStack createEyePearl() {

        ItemStack item = new ItemStack(Material.ENDER_PEARL);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of("eye_pearl"));

        List<String> lore = new ArrayList<>();
        lore.add("§8§l—");
        lore.add("§6» §7It will return you to the position");
        lore.add("§7you were before after " + ChatColor.of("#659a7e") + "5§7 seconds.");
        meta.setDisplayName(ChatColor.of("#659a7e") + "Eye Pearl");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.INFINITY,1,false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createMechanicalEye() {

        ItemStack item = new ItemStack(Material.CARROT_ON_A_STICK);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of("mechanical_eye"));

        List<String> lore = new ArrayList<>();
        lore.add("§8§l—");
        lore.add("§6» §7???");
        meta.setDisplayName(ChatColor.of("#b83d3d") + "Mechanical Eye");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.INFINITY,1,false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }
}
