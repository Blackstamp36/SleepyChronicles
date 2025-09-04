package org.blackstamp.sleepyChronicles.item.vortex;

import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.util.ArrayList;
import java.util.List;

public class vortexItems {
    public ItemStack createVortexTool(Material material, String name, Enchantment enchantment, int level, String data) {
        ItemStack tool = new ItemStack(material);
        ItemMeta meta = tool.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(data));

        List<String> lore = new ArrayList<>();
        switch(material){
            case NETHERITE_HELMET, NETHERITE_CHESTPLATE,
                 NETHERITE_LEGGINGS, NETHERITE_BOOTS:
                lore.add("§8§l—");
                lore.add(ChatColor.of("#4dcbcb") + "Full Set Advantage:");
                lore.add(ChatColor.of("#4dcbcb") + "→ " + ChatColor.of("#4dcbcb") + "+4 §7hearts.");
                lore.add(ChatColor.of("#4dcbcb") + "→ " + ChatColor.of("#4dcbcb") + "+30% §7Projectile damage.");
                lore.add(ChatColor.of("#4dcbcb") + "→ " + ChatColor.of("#4dcbcb") + "+0.25 §7Movement speed.");
                lore.add(ChatColor.of("#4dcbcb") + "→ " + ChatColor.of("#4dcbcb") + "50% §7of not consuming ammo.");
                lore.add(ChatColor.of("#4dcbcb") + "→ §7Shift to " + ChatColor.of("#4dcbcb") + "double jump§7!");
                lore.add(ChatColor.of("#ebc247") + "→ 15s Cooldown.");

                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.DIAMOND, TrimPattern.EYE));
                    tool.setItemMeta(armorMeta);
                }

                break;
            
            case BOW:
                lore.add("§8§l—");
                lore.add("§6» §7Every " + ChatColor.of("#4dcbcb") + "10" + " §7consecutive hits");
                lore.add("§7a" + ChatColor.of("#4dcbcb") + " Blackhole§7 is spawned which attracts");
                lore.add("§7nearby enemies and explodes after " + ChatColor.of("#4dcbcb") + "5s§7.");
                break;

        }

        meta.setDisplayName(ChatColor.of("#4dcbcb") + name);
        meta.setLore(lore);
        meta.setUnbreakable(true);
        meta.addEnchant(enchantment, level, true);
        meta.setCustomModelDataComponent(component);

        tool.setItemMeta(meta);
        return tool;
    }

}
