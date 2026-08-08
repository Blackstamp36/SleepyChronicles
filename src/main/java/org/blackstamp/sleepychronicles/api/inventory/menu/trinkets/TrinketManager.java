package org.blackstamp.sleepychronicles.api.inventory.menu.trinkets;

import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.api.item.ItemManager;
import org.blackstamp.sleepychronicles.api.item.SleepyItems;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TrinketManager {
    private TrinketManager(){}

    private static final HashMap<UUID, Set<String>> TRINKETS_CACHE = new HashMap<>();

    @Nullable
    public static String getTrinketsData(Player player) {
        return PersistentData.get(player, SleepyKeys.TRINKETS_INV.get(), PersistentDataType.STRING);
    }

    @Nullable
    public static Set<String> getTrinketsCache(UUID uuid) {
        return TRINKETS_CACHE.get(uuid);
    }

    public static void removeTrinketsCache(UUID uuid) {
        TRINKETS_CACHE.remove(uuid);
    }

    public static boolean hasTrinketCache(Player p, String trinketID) {
        Set<String> activeTrinkets = TRINKETS_CACHE.get(p.getUniqueId());

        if(activeTrinkets == null || activeTrinkets.isEmpty()) {
            return false;
        }

        return activeTrinkets.contains(trinketID);
    }

    // Saves a 'String set' to the trinkets cache and inserts in the PDC all the set in a single 'String', separated with a comma.
    public static void saveTrinketsData(Player player, ItemStack[] rawItems){
        Set<String> trinketsIDSet = getTrinketIDs(rawItems);
        UUID uuid = player.getUniqueId();

        TRINKETS_CACHE.put(uuid, trinketsIDSet);

        String data = String.join(",",trinketsIDSet);

        PersistentData.set(player, SleepyKeys.TRINKETS_INV.get(), PersistentDataType.STRING, data);
    }

    // Returns a 'string' that contains all of the trinket IDs from a determined ItemStack array.
    public static Set<String> getTrinketIDs(ItemStack[] rawItems){
        Set<String> activeTrinketIDs = new HashSet<>();

        for(ItemStack item : rawItems) {
            if(item == null || item.isEmpty()) {
                continue;
            }

            ItemMeta meta = item.getItemMeta();
            String id = ItemManager.getID(meta);

            if(id == null || id.isEmpty()) {
                continue;
            }

            activeTrinketIDs.add(id);
        }

        return activeTrinketIDs;
    }

    // Loads from the PDC all the trinkets Data and puts it in the static HashSet.
    public static void loadTrinketsData(Player player) {
        String trinketsData = PersistentData.get(player, SleepyKeys.TRINKETS_INV.get(), PersistentDataType.STRING);
        UUID uuid = player.getUniqueId();

        if(trinketsData == null || trinketsData.isEmpty()) {
            TRINKETS_CACHE.put(uuid, new HashSet<>());
            return;
        }

        Set<String> activeTrinketIDs = new HashSet<>();
        String[] dataIDs = trinketsData.split(",");

        for(String id : dataIDs) {
            SleepyItems item = SleepyItems.getItem(id);

            if(item == null) {
                continue;
            }

            activeTrinketIDs.add(id);
        }

        TRINKETS_CACHE.put(uuid,activeTrinketIDs);
    }
}
