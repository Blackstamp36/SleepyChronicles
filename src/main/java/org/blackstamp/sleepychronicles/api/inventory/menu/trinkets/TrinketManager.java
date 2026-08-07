package org.blackstamp.sleepychronicles.api.inventory.menu.trinkets;

import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.api.data.base64.Base64Utils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TrinketManager {
    private TrinketManager(){}

    protected static final int[] TRINKET_SLOTS = new int[]{4, 12, 14, 22};
    protected static HashMap<UUID, List<String>> trinketsCache = new HashMap<>();

    protected static int[] getTrinketSlots() {
        return TRINKET_SLOTS;
    }

    @Nullable
    private String getTrinketData(Player player) {
        return PersistentData.get(player, SleepyKeys.TRINKETS_INV.get(), PersistentDataType.STRING);
    }

    public static boolean hasSpecificTrinket(Player p, String value){
        List<String> trinkets = trinketsCache.get(p.getUniqueId());

        if(trinkets.isEmpty()) return false;

        return trinkets.contains(value);
    }

    public static void saveTrinketsData(Player player, Inventory trinketsInventory){
        ItemStack[] savedTrinkets = new ItemStack[getTrinketSlots().length];
        List<String> memoryTrinkets = new ArrayList<>();

        if(!memoryTrinkets.isEmpty()) org.blackstamp.sleepychronicles.api.item.trinket.TrinketManager.CACHE.put(p.getUniqueId(), memoryTrinkets);

        for(int i = 0; i < savedTrinkets.length ; i++){
            final int currentSlot = getTrinketSlots()[i];

            savedTrinkets[i] = super.inventory.getItem(currentSlot);
        }

        String trinketData = Base64Utils.toBase64(savedTrinkets);
        PersistentData.set(super.p, SleepyKeys.TRINKETS_INV.get(), PersistentDataType.STRING, trinketData);
    }

    // ItemStack related.
    private static ItemStack[] getTrinkets(Inventory trinketsInventory){
        ItemStack[] savedTrinkets = new ItemStack[getTrinketSlots().length];

        for(int i = 0; i < savedTrinkets.length ; i++){
            final int inventorySlot = getTrinketSlots()[i];

            savedTrinkets[i] = trinketsInventory.getItem(inventorySlot);
        }

        return savedTrinkets;
    }
}
