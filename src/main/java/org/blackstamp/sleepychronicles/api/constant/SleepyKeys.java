package org.blackstamp.sleepychronicles.api.constant;

import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.bukkit.NamespacedKey;

public final class SleepyKeys {

    // items
    public static final NamespacedKey ITEM_ID;
    public static final NamespacedKey ITEM_OWNER;
    public static final NamespacedKey ITEM_FAMILY;

    public static final NamespacedKey IS_DOWNED;
    public static final NamespacedKey DOWNED_UUID;

    public static final NamespacedKey IS_REVIVE_STAND;
    public static final NamespacedKey REVIVE_HITS_CURRENT;
    public static final NamespacedKey REVIVE_HITS_REQUIRED;

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

        IS_DOWNED = PersistentData.key("is_downed");
        DOWNED_UUID = PersistentData.key("downed_uuid");

        IS_REVIVE_STAND = PersistentData.key("is_revive_stand");
        REVIVE_HITS_CURRENT = PersistentData.key("revive_hits_current");
        REVIVE_HITS_REQUIRED = PersistentData.key("revive_hits_required");

        MOB_ID = PersistentData.key("mob_id");
        MOB_FAMILY = PersistentData.key("mob_family");

        TRINKETS_INV = PersistentData.key("trinkets_inv");

        TOTEMS = PersistentData.key("totems");
    }

    private SleepyKeys(){}
}
