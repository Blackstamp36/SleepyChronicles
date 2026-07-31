package org.blackstamp.sleepychronicles.api.item;

import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ItemManager {
    public static String getID(ItemMeta meta){ return PersistentData.get(meta, SleepyKeys.ITEM_ID, PersistentDataType.STRING); }
    public static String getFamily(ItemMeta meta){ return PersistentData.get(meta, SleepyKeys.ITEM_FAMILY, PersistentDataType.STRING); }
}
