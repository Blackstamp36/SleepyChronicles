package org.blackstamp.sleepyChronicles.item.pale;

import net.minecraft.world.entity.EquipmentSlot;
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
import java.util.UUID;

public class paleItems {
    public ItemStack createPaleTool(Material material, String name, Enchantment enchantment, int level, String data) {
        ItemStack tool = new ItemStack(material);
        ItemMeta meta = tool.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(data));

        List<String> lore = new ArrayList<>();

        switch(material){
            case NETHERITE_PICKAXE:
                lore.add("§8§l—");
                lore.add("§6» " + ChatColor.of("#bc5c0f") + "Right click §7+" + ChatColor.of("#bc5c0f") + " shift");
                lore.add("§7to switch between modes!");

                NamespacedKey paleSpeedKey = new NamespacedKey(sleepyChronicles.getter(), "pale_speed");
                AttributeModifier speedModifier = new AttributeModifier(
                        paleSpeedKey,
                        1.0,
                        AttributeModifier.Operation.ADD_NUMBER,
                        EquipmentSlotGroup.MAINHAND
                );

                meta.addAttributeModifier(Attribute.ATTACK_SPEED, speedModifier);
                break;

            case NETHERITE_SWORD:
                lore.add("§8§l— §f" + ChatColor.of("#33cc52") + "[♣]");
                lore.add("§6»" + ChatColor.of("#bc5c0f") + " 0.5% §7chance of spawning a");
                lore.add(ChatColor.of("#cfc4c3") + "Pale Soul §7when killing a monster.");
                lore.add(ChatColor.of("#33cc52") + "→ §eGrows up to a" + ChatColor.of("#bc5c0f") + " 5% §ewhen on a " + ChatColor.of("#cfc4c3") + "Pale Garden§e.");
                break;

            case NETHERITE_AXE:
                lore.add("§8§l— §f" + ChatColor.of("#33cc52") + "[♣]");
                lore.add("§6»" + ChatColor.of("#bc5c0f") + " 5% §7chance of dropping 1-3 " + ChatColor.of("#cfc4c3") + "Livid");
                lore.add(ChatColor.of("#cfc4c3") + "Apples §7when breaking a " + ChatColor.of("#cfc4c3") + "Pale Oak Log§7.");
                break;
        }

        meta.setDisplayName(ChatColor.of("#cfc4c3") + name);
        meta.setLore(lore);
        meta.setUnbreakable(true);
        meta.addEnchant(enchantment, level, true);
        meta.setCustomModelDataComponent(component);

        tool.setItemMeta(meta);
        return tool;
    }

    public ItemStack createPaleCrystal(){
        ItemStack item = new ItemStack(Material.GOLD_INGOT);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("pale_crystal")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l—");
        lore.add("§e※ §8Kinda sharp.");
        meta.setDisplayName(ChatColor.of("#cfc4c3") + "Pale Crystal");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.INFINITY,1,false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createPaleShard(){
        ItemStack item = new ItemStack(Material.GOLD_NUGGET);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("pale_shard")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l—");
        lore.add("§e※ §8Essence of the garden.");
        meta.setDisplayName(ChatColor.of("#cfc4c3") + "Pale Shard");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.INFINITY,1,false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createLividApple(){
        ItemStack item = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("livid_apple")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l— §f" + ChatColor.of("#dd1725") + "[\uD83E\uDDEA]");
        lore.add("§6» §7Grants" + ChatColor.of("#bc5c0f") + " Speed II §8(15s) §7and");
        lore.add(ChatColor.of("#bc5c0f") + "Regeneration III" + " §8(5s) §7on consume.");
        meta.setDisplayName(ChatColor.of("#cfc4c3") + "Livid Apple");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.INFINITY,1,false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createWoodenTotem(){
        ItemStack item = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("wooden_totem")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l—");
        lore.add("§6» §7It doesn't count as a totem");
        lore.add("§7consumpt to add into the counter.");
        meta.setDisplayName(ChatColor.of("#8e5f25") + "Wooden Totem");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.INFINITY,1,false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }
}
