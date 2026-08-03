package org.blackstamp.sleepychronicles.game.listener.player.survival.death.totem;

import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class TotemManager {

    public static void set(Player p, Integer value){ PersistentData.set(p, SleepyKeys.TOTEMS.get(), PersistentDataType.INTEGER, value); }

    public static Integer get(Player p){
        if(!TotemManager.has(p)){
            set(p, 0);
            return 0;
        }

        return PersistentData.get(p, SleepyKeys.TOTEMS.get(), PersistentDataType.INTEGER);
    }

    public static boolean has(Player p){ return PersistentData.has(p, SleepyKeys.TOTEMS.get()); }
}
