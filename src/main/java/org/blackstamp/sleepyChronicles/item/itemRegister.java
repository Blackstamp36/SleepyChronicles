package org.blackstamp.sleepyChronicles.item;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.item.drops.*;
import org.blackstamp.sleepyChronicles.item.misc.usableItems;
import org.blackstamp.sleepyChronicles.item.null_items.nullItems;
import org.blackstamp.sleepyChronicles.item.pale.paleItems;
import org.blackstamp.sleepyChronicles.item.trinkets.trinketItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static org.blackstamp.sleepyChronicles.sleepyChronicles.PREFIX;

public class itemRegister {
    globalClass global = new globalClass();
    paleItems paleItems = new paleItems();
    nullItems nullItems = new nullItems();
    creakingDrops creakingDrops = new creakingDrops();
    slimeDrops slimeDrops = new slimeDrops();
    phantomDrops phantomDrops = new phantomDrops();
    ghastDrops ghastDrops = new ghastDrops();
    foxDrops foxDrops = new foxDrops();
    usableItems usableItems = new usableItems();
    trinketItems trinkets  = new trinketItems();

    public void showItems(Player p) {
        Inventory itemsInv = Bukkit.createInventory(null, 54, "§eITEMS");
        ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = blackGlass.getItemMeta();
        meta.setDisplayName("　");
        blackGlass.setItemMeta(meta);

        ItemStack[] tools = {
                paleItems.createPaleTool(
                Material.NETHERITE_SWORD,
                "Pale Sword",
                Enchantment.SHARPNESS,
                5,
                "pale_sword"),

        paleItems.createPaleTool(
                Material.NETHERITE_PICKAXE,
                "Pale Pickaxe",
                Enchantment.EFFICIENCY,
                5,
                "pale_pickaxe"),

        paleItems.createPaleTool(
                Material.NETHERITE_AXE,
                "Pale Axe",
                Enchantment.EFFICIENCY,
                5,
                "pale_axe"),

        paleItems.createPaleTool(
                Material.NETHERITE_SHOVEL,
                "Pale Shovel",
                Enchantment.EFFICIENCY,
                5,
                "pale_shovel")
        };

        ItemStack[] drops = {
                ghastDrops.createBloodTear(),
                creakingDrops.createBobFlesh(),
                nullItems.createNullItem(Material.GUNPOWDER, "Nullpowder"),
                phantomDrops.createLens(),
                slimeDrops.createGhostSeed(),
                foxDrops.createKitsuneTail(),
                paleItems.createPaleCrystal(),
                paleItems.createPaleShard(),
                paleItems.createLividApple()
        };

        ItemStack[] usables = {
                paleItems.createWoodenTotem(),
                usableItems.createEyePearl()
        };

        final int[] chestBorderSlots = {
                0, 1, 2, 3, 4, 5, 6, 7, 8,
                9, 17,
                18, 26,
                27, 35,
                36, 44,
                45, 46, 47, 48, 49, 50, 51, 52, 53
        };

        for(int slot = 0; slot < itemsInv.getSize(); slot++){
        for (int borderSlot : chestBorderSlots) {
            if (slot == borderSlot) {
                itemsInv.setItem(slot, blackGlass);
                }
            }
        }

        itemsInv.addItem(tools);
        itemsInv.addItem(drops);
        itemsInv.addItem(usables);
        itemsInv.addItem(trinkets.trinkets);
        p.sendMessage(PREFIX + "§eOpening items menu!");
        p.openInventory(itemsInv);
        p.playSound(p.getLocation(), Sound.BLOCK_CHEST_OPEN, 1,1.25F);

        }
    }


