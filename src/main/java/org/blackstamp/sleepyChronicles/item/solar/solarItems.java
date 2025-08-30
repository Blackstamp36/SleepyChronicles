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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

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
            case NETHERITE_AXE:
                NamespacedKey solarSpeedKey = new NamespacedKey(sleepyChronicles.getter(), "solar_speed");
                AttributeModifier speedModifier = new AttributeModifier(
                        solarSpeedKey,
                        0.25,
                        AttributeModifier.Operation.ADD_NUMBER,
                        EquipmentSlotGroup.MAINHAND
                );

                meta.addAttributeModifier(Attribute.ATTACK_SPEED, speedModifier);
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
