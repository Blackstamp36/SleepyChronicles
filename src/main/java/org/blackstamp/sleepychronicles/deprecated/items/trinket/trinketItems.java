package org.blackstamp.sleepychronicles.deprecated.items.trinket;

import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.ArrayList;
import java.util.List;

public class trinketItems {
    public ItemStack[] trinkets = {
            createMissingNo(),
            createMegaTear(),
            createBobSoul(),
            createNullTNT(),
            createKitsuneBless(),
            createMyWish(),
            create99Gold(),
            createGhostlyEssence(),
            createQuantumCore(),
            createMementoMori(),
            createNullPointerException(),
            createTearOfDivinity(),
            createBobMiracle(),
            createFoundTNT(),
            createKitsuneHeart(),
            createYourWish(),
            createGoldenTouch(),
            createGhostlySoul(),
            createQuantumReactor(),
            createWarriorEmblem(),
            createRangerEmblem(),
            createSummonerEmblem()
    };

    public ItemStack createMissingNo(){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("missingno")));
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

    public ItemStack createNullTNT(){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("nulltnt")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l— " + ChatColor.of("#ebc247") + "[\uD83C\uDFA3]");
        lore.add(ChatColor.of("#33cc52") + "→ §a-25% Explosion damage.");
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

    public ItemStack createMyWish(){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("my_wish")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l— " + ChatColor.of("#ebc247") + "[\uD83C\uDFA3]");
        lore.add(ChatColor.of("#33cc52") + "→ §a15s of Absorption II when");
        lore.add("§aon 3 hearts or less.");
        lore.add(ChatColor.of("#33cc52") + "→ §c30s of Weakness II §cupon usage.");
        lore.add(ChatColor.of("#ebc247") + "→ 10m Cooldown.");
        meta.setDisplayName(ChatColor.of("#94b4eb") + "My Wish");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack create99Gold(){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("99_gold")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l— " + ChatColor.of("#ebc247") + "[\uD83C\uDFA3]");
        lore.add(ChatColor.of("#33cc52") + "→ §a15% of dropping a gold");
        lore.add("§aingot when killing a monster.");
        lore.add(ChatColor.of("#33cc52") + "→ §c1% of spawning a Creeper.");
        meta.setDisplayName(ChatColor.of("#d39732") + "99.9% Gold");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createGhostlyEssence(){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("ghostly_essence")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l— " + ChatColor.of("#ebc247") + "[\uD83C\uDFA3]");
        lore.add(ChatColor.of("#33cc52") + "→ §a30s of Imperceptibility");
        lore.add("§aupon sneaking.");
        lore.add(ChatColor.of("#ebc247") + "→ 10m Cooldown.");
        meta.setDisplayName(ChatColor.of("#a16e45") + "Ghostly Essence");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createQuantumCore(){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("quantum_core")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l— " + ChatColor.of("#ebc247") + "[\uD83C\uDFA3]");
        lore.add(ChatColor.of("#33cc52") + "→ §aMobs cannot knockback you.");
        lore.add(ChatColor.of("#33cc52") + "→ §cSlowness II permanent.");
        meta.setDisplayName(ChatColor.of("#70ba6d") + "Quantum Core");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createMementoMori(){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("memento_mori")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l— " + ChatColor.of("#ebc247") + "[\uD83C\uDFA3]");
        lore.add(ChatColor.of("#33cc52") + "→ §a50% of receiving the following");
        lore.add("§aupon consuming a totem of undying:");
        lore.add(ChatColor.of("#33cc52") + "→ §aResistance II for 15s.");
        lore.add(ChatColor.of("#33cc52") + "→ §aInstant Health II.");
        lore.add(ChatColor.of("#33cc52") + "→ §c50% of clearing its positive");
        lore.add("§cinitial effects.");
        meta.setDisplayName(ChatColor.of("#d62411") + "Memento mori");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createNullPointerException(){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("nullpointerexception")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l— " + ChatColor.of("#ebc247") + "[\uD83C\uDFA3]");
        lore.add(ChatColor.of("#33cc52") + "→ §a4% that any type of damage");
        lore.add("§ais cancelled.");
        meta.setDisplayName(ChatColor.of("#db1fdb") + "nullPointerException");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createTearOfDivinity(){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("tear_of_divinity")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l— " + ChatColor.of("#ebc247") + "[\uD83C\uDFA3]");
        lore.add(ChatColor.of("#33cc52") + "→ §a-70% Fire or lava damage.");
        meta.setDisplayName(ChatColor.of("#8f594d") + "Tear of Divinity");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createBobMiracle(){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("bob_miracle")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l— " + ChatColor.of("#ebc247") + "[\uD83C\uDFA3]");
        lore.add(ChatColor.of("#33cc52") + "→ §a+4 hearts.");
        meta.setDisplayName(ChatColor.of("#ada19f") + "Bob's Miracle");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createFoundTNT(){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("foundtnt")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l— " + ChatColor.of("#ebc247") + "[\uD83C\uDFA3]");
        lore.add(ChatColor.of("#33cc52") + "→ §a-50% Explosion damage.");
        meta.setDisplayName(ChatColor.of("#db1fdb") + "foundTNT");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createYourWish(){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("your_wish")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l— " + ChatColor.of("#ebc247") + "[\uD83C\uDFA3]");
        lore.add(ChatColor.of("#33cc52") + "→ §a30s of Absorption IV when");
        lore.add("§aon 3 hearts or less.");
        lore.add(ChatColor.of("#33cc52") + "→ §aReceive Instant Health II.");
        lore.add(ChatColor.of("#ebc247") + "→ 5m Cooldown.");
        meta.setDisplayName(ChatColor.of("#94b4eb") + "Your Wish");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createGoldenTouch(){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("golden_touch")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l— " + ChatColor.of("#ebc247") + "[\uD83C\uDFA3]");
        lore.add(ChatColor.of("#33cc52") + "→ §a30% of dropping a 1-5 gold");
        lore.add("§aingots when killing a monster.");
        meta.setDisplayName(ChatColor.of("#d39732") + "Golden Touch");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createGhostlySoul(){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("ghostly_soul")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l— " + ChatColor.of("#ebc247") + "[\uD83C\uDFA3]");
        lore.add(ChatColor.of("#33cc52") + "→ §a60s of Speed II and");
        lore.add("§aImperceptibility upon sneaking.");
        lore.add(ChatColor.of("#ebc247") + "→ 5m Cooldown.");
        meta.setDisplayName(ChatColor.of("#a16e45") + "Ghostly Soul");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createKitsuneHeart(){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("kitsune_heart")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l— " + ChatColor.of("#ebc247") + "[\uD83C\uDFA3]");
        lore.add(ChatColor.of("#33cc52") + "→ §a+30% Attack damage.");
        lore.add(ChatColor.of("#33cc52") + "→ §aStrength I for 5s upon kill.");
        meta.setDisplayName(ChatColor.of("#e4ced1") + "Kitsune's Heart");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createQuantumReactor(){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("quantum_reactor")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l— " + ChatColor.of("#ebc247") + "[\uD83C\uDFA3]");
        lore.add(ChatColor.of("#33cc52") + "→ §aMobs cannot knockback you.");
        lore.add(ChatColor.of("#33cc52") + "→ §aSpeed II permanent.");
        meta.setDisplayName(ChatColor.of("#70ba6d") + "Quantum Reactor");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createWarriorEmblem(){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("warrior_emblem")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l— " + ChatColor.of("#ebc247") + "[\uD83C\uDFA3]");
        lore.add(ChatColor.of("#33cc52") + "→ §a+15% Attack damage.");
        meta.setDisplayName(ChatColor.of("#e9d435") + "Warrior's Emblem");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createRangerEmblem(){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("ranger_emblem")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l— " + ChatColor.of("#ebc247") + "[\uD83C\uDFA3]");
        lore.add(ChatColor.of("#33cc52") + "→ §a+15% Projectile damage.");
        meta.setDisplayName(ChatColor.of("#e9d435") + "Ranger's Emblem");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createSummonerEmblem(){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(new String("summoner_emblem")));
        List<String> lore = new ArrayList<>();
        lore.add("§8§l— " + ChatColor.of("#ebc247") + "[\uD83C\uDFA3]");
        lore.add(ChatColor.of("#33cc52") + "→ §a+15% Summoned damage.");
        meta.setDisplayName(ChatColor.of("#e9d435") + "Summoner's Emblem");
        meta.setLore(lore);
        meta.setCustomModelDataComponent(component);
        item.setItemMeta(meta);

        return item;
    }
}
