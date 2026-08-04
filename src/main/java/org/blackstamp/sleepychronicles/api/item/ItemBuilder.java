package org.blackstamp.sleepychronicles.api.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.blackstamp.sleepychronicles.api.color.BasicPalette;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.api.constant.ConstantFields;
import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.game.items.ItemFamily;
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
        this.setPersistentData(SleepyKeys.ITEM_ID.get(), value);
        return this.setCustomModelData(value);
    }

    public String getID(){ return getPersistentData(SleepyKeys.ITEM_ID.get()); }

    public boolean hasID(){ return PersistentData.has(meta, SleepyKeys.ITEM_ID.get()); }

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
        setPersistentData(SleepyKeys.ITEM_OWNER.get(), nickname);
        return this;
    }

    public String getOwner(){ return getPersistentData(SleepyKeys.ITEM_OWNER.get()); }

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
        setPersistentData(SleepyKeys.ITEM_FAMILY.get(), family.getName());
        return this;
    }

    public String getFamily(){ return getPersistentData(SleepyKeys.ITEM_FAMILY.get()); }

    public boolean hasFamily(){ return PersistentData.has(meta, SleepyKeys.ITEM_FAMILY.get()); }

    /**
     @param newLine is if you want a new line for the lore-value that you're adding.
     */
    public ItemBuilder addLore(String value, String color, boolean newLine){
        if(color == null) color = SleepyPalette.VANILLA.getMiniColor1();

        final ArrayList<Component> lore = new ArrayList<>();

        if(meta.lore() != null){ lore.addAll(meta.lore()); }
        if(lore.isEmpty() || newLine){ lore.add(Component.empty()); }

        Component newText = ConstantFields.MINI_MESSAGE
                .deserialize(color + value)
                .decoration(TextDecoration.ITALIC,false);

        int lastIndex = lore.size() - 1;
        Component lastText = lore.get(lastIndex);

        if(!newLine){ lastText = lastText.append(Component.text(" ")); }

        lastText = lastText.append(newText);

        lore.set(lastIndex,lastText);

        meta.lore(lore);
        return this;
    }

    public ItemBuilder setLore(String value, String color){
        if(color == null) color = SleepyPalette.VANILLA.getMiniColor1();

        final List<Component> lore = splitLoreLines(value, color);

        meta.lore(lore);
        return this;
    }

    // This is commented because I forget easily T-T.
    private @NotNull List<Component> splitLoreLines(String value, String color){
        final ArrayList<Component> lore = new ArrayList<>(); // We declare our 'lore' list.
        final StringBuilder builder = new StringBuilder();
        final int max = 28; // Max characters per line.

        // We check for each word of our 'value' String. in a for-each loop.
        for(String word : value.split("\\s+")){ // Split upon 1 or more blank spaces.

            // If the amount of chars we currently have is higher than the expected, we add it directly to the lore.
            if(builder.length() + word.length() > max && !builder.isEmpty()){
                String prefix = lore.isEmpty() ? (BasicPalette.DARK_GRAY.getColor() + "|" + color) : color;

                lore.add(ConstantFields.MINI_MESSAGE.deserialize(prefix + builder).decoration(TextDecoration.ITALIC,false));
                builder.setLength(0); // We set our current characters to zero for the next line.
            }

            // If there's any word already in the line, we append with a 'space' it so it doesn't look 'raw'.
            if(!builder.isEmpty()){ builder.append(" "); }

            builder.append(word);
        }

        // Add any word that was left on the builder.
        if(!builder.isEmpty()){
            lore.add(ConstantFields.MINI_MESSAGE.deserialize(color + builder).decoration(TextDecoration.ITALIC, false));
        }

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
