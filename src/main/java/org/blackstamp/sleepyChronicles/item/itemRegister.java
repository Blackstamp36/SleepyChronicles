package org.blackstamp.sleepyChronicles.item;

import org.blackstamp.sleepyChronicles.item.drops.*;
import org.blackstamp.sleepyChronicles.item.misc.usableItems;
import org.blackstamp.sleepyChronicles.item.null_items.nullItems;
import org.blackstamp.sleepyChronicles.item.pale.paleItems;
import org.blackstamp.sleepyChronicles.item.solar.solarItems;
import org.blackstamp.sleepyChronicles.item.trinkets.trinketItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static org.blackstamp.sleepyChronicles.command.staffCRegister.itemsPageOne;
import static org.blackstamp.sleepyChronicles.command.staffCRegister.itemsPageTwo;

public class itemRegister {

    solarItems solarItems = new solarItems();
    witherDrops witherDrops = new witherDrops();
    paleItems paleItems = new paleItems();
    nullItems nullItems = new nullItems();
    creakingDrops creakingDrops = new creakingDrops();
    slimeDrops slimeDrops = new slimeDrops();
    phantomDrops phantomDrops = new phantomDrops();
    ghastDrops ghastDrops = new ghastDrops();
    foxDrops foxDrops = new foxDrops();
    usableItems usableItems = new usableItems();
    trinketItems trinkets = new trinketItems();

    final int[] chestBorderSlots = {
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 17,
            18, 26,
            27, 35,
            36, 44,
            46, 47, 48, 49, 50, 51, 52
    };

    public Inventory getItemsPageOne() {
        Inventory itemsInv = Bukkit.createInventory(null, 54, "§eITEMS");
        ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = blackGlass.getItemMeta();
        meta.setDisplayName("　");
        blackGlass.setItemMeta(meta);
        ItemStack nextPage = new ItemStack(Material.LIME_DYE);
        ItemMeta npMeta = nextPage.getItemMeta();
        npMeta.setDisplayName("§aNext");
        nextPage.setItemMeta(npMeta);
        ItemStack previousPage = new ItemStack(Material.RED_DYE);
        ItemMeta ppMeta = previousPage.getItemMeta();
        ppMeta.setDisplayName("§cBack");
        previousPage.setItemMeta(ppMeta);

        ItemStack[] paleTools = {
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

        ItemStack[] solarTools = {
                solarItems.createSolarTool(
                        Material.NETHERITE_SWORD,
                        "Solar Sword",
                        Enchantment.SHARPNESS,
                        5,
                        "solar_sword"),
                solarItems.createSolarTool(
                        Material.NETHERITE_PICKAXE,
                        "Solar Pickaxe",
                        Enchantment.EFFICIENCY,
                        5,
                        "solar_pickaxe"),
                solarItems.createSolarTool(
                        Material.NETHERITE_AXE,
                        "Solar Axe",
                        Enchantment.EFFICIENCY,
                        5,
                        "solar_axe"),
                solarItems.createSolarTool(
                        Material.NETHERITE_SHOVEL,
                        "Solar Shovel",
                        Enchantment.EFFICIENCY,
                        5,
                        "solar_shovel")
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
                paleItems.createLividApple(),
                witherDrops.createSoulOfVision()
        };

        ItemStack[] usables = {
                paleItems.createWoodenTotem(),
                usableItems.createEyePearl()
        };

        for (int slot = 0; slot < itemsInv.getSize(); slot++) {
            for (int borderSlot : chestBorderSlots) {
                if (slot == borderSlot) {
                    itemsInv.setItem(slot, blackGlass);
                }
            }
        }

        itemsInv.setItem(45, previousPage);
        itemsInv.setItem(53, nextPage);

        itemsInv.addItem(paleTools);
        itemsInv.addItem(solarTools);
        itemsInv.addItem(usables);
        itemsInv.addItem(drops);

        return itemsInv;
    }

    public Inventory getItemsPageTwo() {
        Inventory itemsInv = Bukkit.createInventory(null, 54, "§eITEMS");
        ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = blackGlass.getItemMeta();
        meta.setDisplayName("　");
        blackGlass.setItemMeta(meta);
        ItemStack nextPage = new ItemStack(Material.LIME_DYE);
        ItemMeta npMeta = nextPage.getItemMeta();
        npMeta.setDisplayName("§aNext");
        nextPage.setItemMeta(npMeta);
        ItemStack previousPage = new ItemStack(Material.RED_DYE);
        ItemMeta ppMeta = previousPage.getItemMeta();
        ppMeta.setDisplayName("§cBack");
        previousPage.setItemMeta(ppMeta);

        for (int slot = 0; slot < itemsInv.getSize(); slot++) {
            for (int borderSlot : chestBorderSlots) {
                if (slot == borderSlot) {
                    itemsInv.setItem(slot, blackGlass);
                }
            }
        }

        itemsInv.setItem(45, previousPage);
        itemsInv.setItem(53, nextPage);

        itemsInv.addItem(trinkets.trinkets);
        return itemsInv;
    }

    public Inventory getInventoryForPage(int page) {
        switch (page) {
            case 1 -> {
                return itemsPageOne;
            }

            case 2 -> {
                return itemsPageTwo;
            }

        }

        System.out.println("Couldn't find a correct page! Result is null");
        return null;
    }

    public int getPageNumber(Inventory inv) {
        if (inv.equals(itemsPageOne)) {
            return 1;

        } else if (inv.equals(itemsPageTwo)) {
            return 2;
        }

        System.out.println("Couldn't get the page number for inventory: " + inv);
        return 0;
    }
}


