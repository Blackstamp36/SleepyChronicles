package org.blackstamp.sleepychronicles.api.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.blackstamp.sleepychronicles.api.constant.ConstantFields;
import org.blackstamp.sleepychronicles.api.constant.ConstantColors;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.game.items.ItemFamily;
import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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

public class ItemBuilder implements Cloneable {

    private final ItemStack item;
    private final ItemMeta meta;

    public ItemBuilder(ItemStack item) throws IllegalArgumentException{
        if(item == null) throw new IllegalArgumentException("Item cannot be null!");

        this.item = item;
        this.meta = item.getItemMeta();
    }

    public ItemBuilder(Material material) throws IllegalArgumentException{
        if(material == null) throw new IllegalArgumentException("Material cannot be null!");

        this.item = ItemStack.of(material);
        this.meta = item.getItemMeta();
    }

    public ItemBuilder setDisplay(String display){
        meta.displayName(ConstantFields.MINI_MESSAGE
                .deserialize(display)
                .decoration(TextDecoration.ITALIC,false));
        return this;
    }

    public ItemBuilder setDisplay(Component display){
        meta.displayName(display.decoration(TextDecoration.ITALIC,false));
        return this;
    }

    public ItemBuilder setID(String value){
        this.setPersistentData(SleepyKeys.ITEM_ID, value);
        return this.setCustomModelData(value);
    }

    public String getID(){ return getPersistentData(SleepyKeys.ITEM_ID); }

    public boolean hasID(){ return PersistentData.has(meta, SleepyKeys.ITEM_ID); }

    public ItemBuilder setAmount(int value){
        item.setAmount(value);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level){
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder removeEnchant(Enchantment enchantment){
        meta.removeEnchant(enchantment);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean value){
        meta.setUnbreakable(value);
        meta.setDamageResistant(DamageTypeTags.IS_EXPLOSION);
        meta.setDamageResistant(DamageTypeTags.IS_FIRE);
        return this;
    }

    public ItemBuilder setGlint(boolean value){
        meta.setEnchantmentGlintOverride(value);
        return this;
    }

    public ItemBuilder setFlags(ItemFlag... flags){
        meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder setOwner(String nickname){
        if(!(meta instanceof SkullMeta skull)) return this;

        OfflinePlayer p = Bukkit.getOfflinePlayer(nickname);

        skull.setOwningPlayer(p);
        setPersistentData(SleepyKeys.ITEM_OWNER, nickname);
        return this;
    }

    public String getOwner(){ return getPersistentData(SleepyKeys.ITEM_OWNER); }

    public ItemBuilder setCustomModelData(String... value){
        ArrayList<String> valueArray = new ArrayList<>(Arrays.asList(value));
        CustomModelDataComponent cmdComponent = meta.getCustomModelDataComponent();
        cmdComponent.setStrings(valueArray);

        meta.setCustomModelDataComponent(cmdComponent);
        return this;
    }

    public void setPersistentData(NamespacedKey key, String value){
        PersistentData.set(meta, key, PersistentDataType.STRING, value);
    }

    public String getPersistentData(NamespacedKey key){ return PersistentData.get(meta, key, PersistentDataType.STRING); }

    public ItemBuilder setFamily(ItemFamily family){
        setPersistentData(SleepyKeys.ITEM_FAMILY, family.getName());

        return this;
    }

    public String getFamily(){ return getPersistentData(SleepyKeys.ITEM_FAMILY); }

    public boolean hasFamily(){ return PersistentData.has(meta, SleepyKeys.ITEM_FAMILY); }

    public ItemBuilder addLore(String value, String color, boolean extra){
        final ArrayList<Component> lore = new ArrayList<>();

        if(meta.lore() != null) lore.addAll(meta.lore());

        if(extra) lore.add(Component.text(" "));

        lore.add(ConstantFields.MINI_MESSAGE
                .deserialize(color + value)
                .decoration(TextDecoration.ITALIC,false));
        meta.lore(lore);
        return this;
    }

    public ItemBuilder setLore(String value, String color){
        final List<Component> lore = splitLoreLines(value, color);

        meta.lore(lore);
        return this;
    }

    // This is commented because I forget easily T-T.
    private @NotNull List<Component> splitLoreLines(String value, String color){
        final ArrayList<Component> lore = new ArrayList<>(); // We declare our 'lore' list.
        final StringBuilder builder = new StringBuilder(ConstantColors.DARK_GRAY + "|" + color); // We define the 'first' line.
        final int max = 28; // Max characters per line.

        // We check for each word of our 'value' String. in a for-each loop.
        for(String word : value.split("\\s+")){ // Split upon 1 or more blank spaces.
            final int chars = word.length() + builder.length() + 1; // Current characters on the builder.
            int current = max; // Temporary variable for our max chars.

            if(lore.isEmpty()) current += builder.length();

            if(chars > current){ // If the amount of chars we currently have is higher than the expected, we add it directly to the lore.
                lore.add(ConstantFields.MINI_MESSAGE.deserialize(color + builder).decoration(TextDecoration.ITALIC,false));
                builder.setLength(0); // We set our current characters to zero.
            }

            // If there's any word already in the line, we append with a 'space' it so it doesn't look 'raw'.
            if(!builder.isEmpty()) builder.append(" ").append(word);
            else builder.append(word);
        }

        // Add any word that was left on the builder.
        if(!builder.isEmpty()) lore.add(ConstantFields.MINI_MESSAGE.deserialize(color + builder).decoration(TextDecoration.ITALIC,false));

        return lore; // We return the list (lore) properly.
    }

    public ItemStack build(){
        item.setItemMeta(meta);
        return item;
    }

    public net.minecraft.world.item.ItemStack getAsNMS() { return CraftItemStack.asNMSCopy(item); }

    @Override
    public ItemBuilder clone() {
        try {
            return (ItemBuilder) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
