package org.blackstamp.sleepyChronicles.item.solar;

import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.util.ArrayList;
import java.util.List;

public class solarItems {
    public ItemStack createSolarTool(Material material, String name, Enchantment enchantment, int level, String data) {
        ItemStack tool = new ItemStack(material);
        ItemMeta meta = tool.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(data));

        List<String> lore = new ArrayList<>();
        switch(material){
            case NETHERITE_HELMET, NETHERITE_CHESTPLATE,
                 NETHERITE_LEGGINGS, NETHERITE_BOOTS:
                lore.add("§8§l—");
                lore.add(ChatColor.of("#cc9933") + "Full Set Advantage:");
                lore.add(ChatColor.of("#33cc52") + "→ " + ChatColor.of("#cc9933") + "+6 §7hearts.");
                lore.add(ChatColor.of("#33cc52") + "→ " + ChatColor.of("#cc9933") + "+15% §7Attack damage.");
                lore.add(ChatColor.of("#33cc52") + "→ " + ChatColor.of("#cc9933") + "-10% §7Incoming melee damage.");
                lore.add(ChatColor.of("#33cc52") + "→ §7Immunity to " + ChatColor.of("#cc9933") + "Fire ticks§7.");
                lore.add(ChatColor.of("#33cc52") + "→ §7Lights on " + ChatColor.of("#cc9933") + "fire §7monsters");
                lore.add("§7that attack you physically.");
                lore.add(ChatColor.of("#cc9933") + "→ §7Shift to enable a " + ChatColor.of("#cc9933") + "shield§7!");
                lore.add(ChatColor.of("#ebc247") + "→ 4m Cooldown.");

                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.GOLD, TrimPattern.SPIRE));
                    tool.setItemMeta(armorMeta);
                }

                break;
            
            case NETHERITE_SWORD:
                lore.add("§8§l—");
                lore.add("§6» §7Every " + ChatColor.of("#cc9933") + "10" + " §7consecutive hits");
                lore.add("§7an explosion is spawned which deals");
                lore.add("§7the" + ChatColor.of("#cc9933") + " double" + " §7of your current health");
                lore.add("§7as damage.");
                break;

        }

        meta.setDisplayName(ChatColor.of("#cc9933") + name);
        meta.setLore(lore);
        meta.setUnbreakable(true);
        meta.addEnchant(enchantment, level, true);
        meta.setCustomModelDataComponent(component);

        tool.setItemMeta(meta);
        return tool;
    }

}
