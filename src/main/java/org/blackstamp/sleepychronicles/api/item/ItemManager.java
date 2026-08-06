package org.blackstamp.sleepychronicles.api.item;

import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ItemManager {
    private ItemManager(){}

    public static String getID(ItemMeta meta){
        return PersistentData.get(meta, SleepyKeys.ITEM_ID.get(), PersistentDataType.STRING);
    }
    public static String getFamily(ItemMeta meta){
        return PersistentData.get(meta, SleepyKeys.ITEM_FAMILY.get(), PersistentDataType.STRING);
    }
    public static String getArmorID(ItemStack armorPiece){
        if(armorPiece == null) return null;

        ItemMeta meta = armorPiece.getItemMeta();

        if(meta == null) return null;

        return getID(meta);
    }
}
