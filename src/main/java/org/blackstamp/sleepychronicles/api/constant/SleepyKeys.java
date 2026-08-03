package org.blackstamp.sleepychronicles.api.constant;

import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.bukkit.NamespacedKey;

public enum SleepyKeys {

    ITEM_ID, ITEM_OWNER, ITEM_FAMILY,

    IS_DOWNED, IS_REVIVE_STAND,

    MOB_ID, MOB_FAMILY,

    TRINKETS_INV, TOTEMS;

    NamespacedKey key;

    public NamespacedKey get(){
        if(this.key == null){ PersistentData.key(this.name().toLowerCase()); }

        return this.key;
    }
}
