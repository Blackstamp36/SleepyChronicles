package org.blackstamp.sleepyChronicles.item.trinkets;

import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class trinketItems {
    public ItemStack[] trinkets = {
            createMissingNo(),
            createBobSoul(),
            createMegaTear(),
            createNullTNT(),
            createKitsuneBless()
    };

    public ItemStack createMissingNo(){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("missingNo")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l— " + ChatColor.of("#ebc247") + "[\uD83C\uDFA3]");
        lore.add(ChatColor.of("#33cc52") + "→ §a2% that any type of damage");
        lore.add("§ais cancelled.");
        lore.add(ChatColor.of("#33cc52") + "→ §eWill break on usage.");
        meta.setDisplayName(ChatColor.of("#db1fdb") + "missingNo");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createBobSoul(){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("bob_soul")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l— " + ChatColor.of("#ebc247") + "[\uD83C\uDFA3]");
        lore.add(ChatColor.of("#33cc52") + "→ §a+2 hearts.");
        lore.add(ChatColor.of("#33cc52") + "→ §aResistance I permanent.");
        lore.add(ChatColor.of("#33cc52") + "→ §cSlowness II permanent.");
        meta.setDisplayName(ChatColor.of("#ada19f") + "Bob's Soul");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createMegaTear(){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("mega_tear")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l— " + ChatColor.of("#ebc247") + "[\uD83C\uDFA3]");
        lore.add(ChatColor.of("#33cc52") + "→ §a-35% Fire or lava damage.");
        lore.add(ChatColor.of("#33cc52") + "→ §c+50% Projectile or fall damage.");
        meta.setDisplayName(ChatColor.of("#8f594d") + "Mega-tear");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createNullTNT(){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("nullTNT")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l— " + ChatColor.of("#ebc247") + "[\uD83C\uDFA3]");
        lore.add(ChatColor.of("#33cc52") + "→ §a-35% Explosion damage.");
        lore.add(ChatColor.of("#33cc52") + "→ §c-1 heart.");
        meta.setDisplayName(ChatColor.of("#db1fdb") + "nullTNT");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createKitsuneBless(){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("kitsune_bless")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l— " + ChatColor.of("#ebc247") + "[\uD83C\uDFA3]");
        lore.add(ChatColor.of("#33cc52") + "→ §a+15% Attack damage.");
        lore.add(ChatColor.of("#33cc52") + "→ §c+15% Incoming damage.");
        meta.setDisplayName(ChatColor.of("#e4ced1") + "Kitsune's Bless");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }
}
