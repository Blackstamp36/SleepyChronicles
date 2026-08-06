package org.blackstamp.sleepychronicles.api.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
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
import java.util.UUID;

public class ItemBuilder<T extends ItemBuilder<T>> {
    private final ItemStack item;
    private final ItemMeta meta;

    @SuppressWarnings("unchecked")
    protected T self() {
        return (T) this;
    }

    public ItemBuilder(ItemStack item) throws IllegalArgumentException {
        if(item == null) throw new IllegalArgumentException("Item cannot be null!");

        this.item = item;
        this.meta = item.getItemMeta();
    }

    public ItemBuilder(Material material) throws IllegalArgumentException {
        if(material == null) throw new IllegalArgumentException("Material cannot be null!");

        this.item = ItemStack.of(material);
        this.meta = item.getItemMeta();
    }

    public T setDisplay(String display, SleepyPalette palette) {
        return this.setDisplay(display,palette,0);
    }

    public T setDisplay(String display, SleepyPalette palette, int colorType) {
        Component displayComponent = Component.text(display)
                .color(TextColor.fromCSSHexString(palette.getHex(colorType)))
                .decoration(TextDecoration.ITALIC,false);

        meta.displayName(displayComponent);
        return this.self();
    }

    public T setID(String value) {
        this.setPersistentData(SleepyKeys.ITEM_ID.get(), value);

        return this.self();
    }

    public String getID() {
        return getPersistentData(SleepyKeys.ITEM_ID.get());
    }

    public boolean hasID() {
        return PersistentData.has(meta, SleepyKeys.ITEM_ID.get());
    }

    public void setAmount(int value) {
        item.setAmount(value);
    }

    public T addEnchant(Enchantment enchantment, int level) {
        meta.addEnchant(enchantment, level, true);

        return this.self();
    }

    public T removeEnchant(Enchantment enchantment) {
        meta.removeEnchant(enchantment);

        return this.self();
    }

    public T setUnbreakable(boolean value) {
        meta.setUnbreakable(value);
        meta.setDamageResistant(DamageTypeTags.IS_EXPLOSION);
        meta.setDamageResistant(DamageTypeTags.IS_FIRE);

        return this.self();
    }

    public T setGlint(boolean value) {
        meta.setEnchantmentGlintOverride(value);

        return this.self();
    }

    public T setFlags(ItemFlag... flags) {
        meta.addItemFlags(flags);

        return this.self();
    }

    public T setOwner(UUID ownerUUID) {
        if(!(meta instanceof SkullMeta skull)) return this.self();

        OfflinePlayer ownerPlayer = Bukkit.getOfflinePlayer(ownerUUID);

        skull.setOwningPlayer(ownerPlayer);
        this.setPersistentData(SleepyKeys.ITEM_OWNER.get(), ownerPlayer.getName());

        return this.self();
    }

    public String getOwner() {
        return this.getPersistentData(SleepyKeys.ITEM_OWNER.get());
    }

    public ItemBuilder setCustomModelData(String... value){
        ArrayList<String> valueArray = new ArrayList<>(Arrays.asList(value));
        CustomModelDataComponent cmdComponent = meta.getCustomModelDataComponent();
        cmdComponent.setStrings(valueArray);

        meta.setCustomModelDataComponent(cmdComponent);
        return this;
    }

    public void setPersistentData(NamespacedKey key, String value) {
        PersistentData.set(meta, key, PersistentDataType.STRING, value);
    }

    public String getPersistentData(NamespacedKey key) {
        return PersistentData.get(meta, key, PersistentDataType.STRING);
    }

    public T setFamily(ItemFamily family) {
        setPersistentData(SleepyKeys.ITEM_FAMILY.get(), family.getName());

        return this.self();
    }

    public String getFamily() {
        return getPersistentData(SleepyKeys.ITEM_FAMILY.get());
    }

    public boolean hasFamily() {
        return PersistentData.has(meta, SleepyKeys.ITEM_FAMILY.get());
    }

    /**
     @param newLine is if you want a new line for the lore-value that you're adding.
     */
    public T addLore(String value, TextColor textColor, boolean newLine) {
        final ArrayList<Component> lore = new ArrayList<>();

        if(meta.lore() != null) {
            lore.addAll(meta.lore());
        }
        if(lore.isEmpty() || newLine) {
            lore.add(Component.empty());
        }

        Component newText = Component.empty()
                .append(Component.text(value).color(textColor).decoration(TextDecoration.ITALIC,false));

        int lastIndex = lore.size() - 1;
        Component lastText = lore.get(lastIndex);

        if(!newLine) {
            lastText = lastText.append(Component.text(" "));
        }

        lastText = lastText.append(newText);
        lore.set(lastIndex,lastText);
        meta.lore(lore);

        return this.self();
    }

    public T setLore(String value, TextColor textColor) {
        final List<Component> lore = splitLoreLines(value, textColor);

        meta.lore(lore);

        return this.self();
    }

    // This is commented because I forget easily T-T.
    private @NotNull List<Component> splitLoreLines(String value, TextColor textColor) {
        final ArrayList<Component> lore = new ArrayList<>(); // We declare our 'lore' list.
        final StringBuilder builder = new StringBuilder();
        final int max = 28; // Max characters per line.

        // We check for each word of our 'value' String. in a for-each loop.
        for(String word : value.split("\\s+")) { // Split upon 1 or more blank spaces.

            // If the amount of chars we currently have is higher than the expected, we add it directly to the lore.
            if(builder.length() + word.length() > max && !builder.isEmpty()) {
                Component prefix = lore.isEmpty()
                        ?
                        Component.empty()
                        .append(Component.text("|")).color(NamedTextColor.DARK_GRAY)
                        :
                        Component.text(" ");

                lore.add(Component.empty()
                        .append(prefix)
                        .append(Component.text(builder.toString()).color(textColor)).decoration(TextDecoration.ITALIC,false)
                );

                builder.setLength(0); // We set our current characters to zero for the next line.
            }

            // If there's any word already in the line, we append with a 'space' it so it doesn't look 'raw'.
            if(!builder.isEmpty()) {
                builder.append(" ");
            }

            builder.append(word);
        }

        // Add any word that was left on the builder.
        if(!builder.isEmpty()) {
            lore.add(Component.empty()
                    .append(Component.text(builder.toString()).color(textColor))
            );
        }

        return lore; // We return the list (lore) properly.
    }

    public ItemStack build() {
        item.setItemMeta(meta);

        return item;
    }

    public net.minecraft.world.item.ItemStack getAsNMS() {
        return CraftItemStack.asNMSCopy(item);
    }
}
