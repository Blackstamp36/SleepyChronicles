package org.blackstamp.sleepychronicles.api.item;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomModelData;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.game.items.ItemFamily;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.tag.DamageTypeTags;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class ItemManager implements Cloneable {
    final ItemStack item;
    ItemMeta meta;

    public ItemManager(ItemStack item) { this.item = item; }
    public ItemManager(Material material){ this.item = ItemStack.of(material); }

    public ItemManager setDisplay(String display){
        meta = item.getItemMeta();

        meta.displayName(MiniMessage.miniMessage()
                .deserialize(display)
                .decoration(TextDecoration.ITALIC,false));
        item.setItemMeta(meta);
        return this;
    }

    public ItemManager setDisplay(Component display){
        meta = item.getItemMeta();

        meta.displayName(display.decoration(TextDecoration.ITALIC,false));
        item.setItemMeta(meta);
        return this;
    }

    public ItemManager setID(String value) {
        meta = item.getItemMeta();

        PersistentData.set(meta, "id", PersistentDataType.STRING, value);
        item.setItemMeta(meta);
        return this.setCustomModelData(value);
    }

    public String getID(){ return PersistentData.get(item.getItemMeta(), "id", PersistentDataType.STRING); }

    public ItemManager setAmount(int value){
        item.setAmount(value);
        return this;
    }

    public ItemManager addEnchant(Enchantment enchantment, int level){
        meta = item.getItemMeta();

        meta.addEnchant(enchantment, level, true);
        item.setItemMeta(meta);
        return this;
    }

    public ItemManager removeEnchant(Enchantment enchantment){
        meta = item.getItemMeta();

        meta.removeEnchant(enchantment);
        item.setItemMeta(meta);
        return this;
    }

    public ItemManager setUnbreakable(boolean value){
        meta = item.getItemMeta();

        meta.setUnbreakable(value);
        meta.setDamageResistant(DamageTypeTags.IS_EXPLOSION);
        meta.setDamageResistant(DamageTypeTags.IS_FIRE);
        item.setItemMeta(meta);
        return this;
    }

    public ItemManager setGlint(boolean value){
        meta = item.getItemMeta();

        if(value){
            meta.addEnchant(Enchantment.INFINITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_STORED_ENCHANTS);
        } else {
            meta.removeEnchant(Enchantment.INFINITY);
            meta.removeItemFlags(ItemFlag.HIDE_STORED_ENCHANTS);
        }

        item.setItemMeta(meta);
        return this;
    }

    public ItemManager setFlags(ItemFlag... flags){
        meta = item.getItemMeta();

        meta.addItemFlags(flags);
        item.setItemMeta(meta);
        return this;
    }

    public ItemManager setOwner(String nickname){
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        OfflinePlayer p = Bukkit.getOfflinePlayer(nickname);

        meta.setOwningPlayer(p);
        PersistentData.set(meta, "owner", PersistentDataType.STRING, nickname);
        item.setItemMeta(meta);
        return this;
    }

    public String getOwner(){ return PersistentData.get(item.getItemMeta(), "owner", PersistentDataType.STRING); }

    public ItemManager setCustomData(String key, String value){
        meta = item.getItemMeta();

        PersistentData.set(meta, key, PersistentDataType.STRING, value);
        item.setItemMeta(meta);
        return this;
    }

    public ItemManager setCustomModelData(String... value){
        net.minecraft.world.item.ItemStack nmsItem = this.getAsNMS();
        ArrayList<String> valueArray = new ArrayList<>(Arrays.asList(value));
        CustomModelData customModelData = new CustomModelData(
                new ArrayList<>(),
                new ArrayList<>(),
                valueArray,
                new ArrayList<>()
                );

        nmsItem.applyComponents(DataComponentMap.builder().set(DataComponents.CUSTOM_MODEL_DATA, customModelData).build());
        this.meta = nmsItem.asBukkitCopy().getItemMeta();
        item.setItemMeta(meta);
        return this;
    }

    public String getCustomData(String key){ return PersistentData.get(item.getItemMeta(), key, PersistentDataType.STRING); }

    public ItemManager setFamily(ItemFamily family){
        meta = item.getItemMeta();

        PersistentData.set(meta, "family", PersistentDataType.STRING, family.name());
        item.setItemMeta(meta);
        return this;
    }

    public String getFamily(){ return PersistentData.get(item.getItemMeta(), "family", PersistentDataType.STRING); }

    public ItemManager addLore(String value){
        meta = item.getItemMeta();
        final ArrayList<Component> lore = new ArrayList<>();

        if(meta.lore() != null) lore.addAll(meta.lore());

        lore.add(MiniMessage.miniMessage()
                .deserialize(value)
                .decoration(TextDecoration.ITALIC,false));
        meta.lore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemManager setLore(String value){
        meta = item.getItemMeta();
        final ArrayList<Component> lore = new ArrayList<>();

        lore.add(MiniMessage.miniMessage()
                .deserialize(value)
                .decoration(TextDecoration.ITALIC,false));
        meta.lore(lore);
        item.setItemMeta(meta);
        return this;
    }

    private @NotNull List<String> splitLoreLines(String value, String color){
        final ArrayList<String> lore = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        final byte max = 20;
        String line = "";

        for(String word : value.split("//s+")){
            if(!builder.isEmpty() && builder.length() + word.length() > max){
                lore.add(line);
                builder.setLength(0);
            }

            builder.append(word);
            line = color + builder.toString().trim();
        }

        if(!builder.isEmpty()) lore.add(line);
        return lore;
    }

    public ItemStack build(){ return item; }

    public net.minecraft.world.item.ItemStack getAsNMS() { return CraftItemStack.asNMSCopy(item); }

    @Override
    public ItemManager clone() {
        try {
            return (ItemManager) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
