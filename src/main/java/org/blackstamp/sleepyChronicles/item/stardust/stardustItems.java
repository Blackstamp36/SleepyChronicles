package org.blackstamp.sleepyChronicles.item.stardust;

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

public class stardustItems {
    public ItemStack createStardustTool(Material material, String name, Enchantment enchantment, int level, String data) {
        ItemStack tool = new ItemStack(material);
        ItemMeta meta = tool.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(data));

        List<String> lore = new ArrayList<>();
        switch(material) {
            case NETHERITE_HELMET, NETHERITE_CHESTPLATE,
                 NETHERITE_LEGGINGS, NETHERITE_BOOTS:
                lore.add("§8§l—");
                lore.add(ChatColor.of("#64c7e8") + "Full Set Advantage:");
                lore.add(ChatColor.of("#33cc52") + "→ " + ChatColor.of("#64c7e8") + "+4 §7hearts.");
                lore.add(ChatColor.of("#33cc52") + "→ " + ChatColor.of("#64c7e8") + "+4 §7summons.");
                lore.add(ChatColor.of("#33cc52") + "→ " + ChatColor.of("#64c7e8") + "+30% §7of summoning damage.");
                lore.add(ChatColor.of("#64c7e8") + "→ §7Shift to summon a " + ChatColor.of("#64c7e8") + "golem§7!");
                lore.add(ChatColor.of("#ebc247") + "→ 2m Cooldown.");

                if(meta instanceof ArmorMeta armorMeta) {
                    armorMeta.setTrim(new ArmorTrim(TrimMaterial.LAPIS, TrimPattern.VEX));
                    tool.setItemMeta(armorMeta);
                }
                break;

            case STICK:
                lore.add("§8§l—");
                lore.add("§6» §7Spawns a" + ChatColor.of("#64c7e8") + " Stardust Phantom§7.");
                lore.add(ChatColor.of("#ebc247") + "→ Only 1 at the time.");
                break;

        }

        meta.setDisplayName(ChatColor.of("#64c7e8") + name);
        meta.setLore(lore);
        meta.setUnbreakable(true);
        meta.addEnchant(enchantment, level, true);
        meta.setCustomModelDataComponent(component);

        tool.setItemMeta(meta);
        return tool;
    }

}
