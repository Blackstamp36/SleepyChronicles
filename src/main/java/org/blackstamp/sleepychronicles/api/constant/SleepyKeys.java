package org.blackstamp.sleepychronicles.api.constant;

import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.bukkit.NamespacedKey;

public final class SleepyKeys {

    // items
    public static final NamespacedKey ITEM_ID;
    public static final NamespacedKey ITEM_OWNER;
    public static final NamespacedKey ITEM_FAMILY;

    public static final NamespacedKey DOWNED;

    // mobs
    public static final NamespacedKey MOB_ID;
    public static final NamespacedKey MOB_FAMILY;

    // trinkets
    public static final NamespacedKey TRINKETS_INV;

    // totems
    public static final NamespacedKey TOTEMS;

    static{
        ITEM_ID = PersistentData.key("item_id");
        ITEM_OWNER = PersistentData.key("item_owner");
        ITEM_FAMILY = PersistentData.key("item_family");

        DOWNED = PersistentData.key("false");

        MOB_ID = PersistentData.key("mob_id");
        MOB_FAMILY = PersistentData.key("mob_family");

        TRINKETS_INV = PersistentData.key("trinkets_inv");

        TOTEMS = PersistentData.key("totems");
    }

    private SleepyKeys(){}
}
