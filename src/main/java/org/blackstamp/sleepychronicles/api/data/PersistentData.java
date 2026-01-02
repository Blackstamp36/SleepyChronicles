package org.blackstamp.sleepychronicles.api.data;

import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class PersistentData {

    @Nullable
    public static <T,Z> Z get(PersistentDataHolder holder, String key, PersistentDataType<T,Z> type){

        return PersistentData.getDataContainer(holder).get(key(key), type);
    }

    public static <T,Z> void set(PersistentDataHolder holder, String key, PersistentDataType<T, Z> type, Z value){
        PersistentData.getDataContainer(holder).set(PersistentData.key(key), type, value);
    }

    public static <T,Z> boolean equals(PersistentDataHolder holder, String key, PersistentDataType<T, Z> type, Z value) {
        if(!PersistentData.has(holder, key, type, value)) return false;

        return Objects.equals(get(holder, key, type), value);
    }

    public static <T,Z> boolean has(PersistentDataHolder holder, String key, PersistentDataType<T, Z> type, Z value){
        if(holder == null) return false;

        return holder.getPersistentDataContainer().has(key(key));
    }

    public static <T,Z> void remove(PersistentDataHolder holder, String key){
        if(holder == null) return;

        getDataContainer(holder).remove(key(key));
    }

    @NotNull
    @Contract("_ -> new")
    public static NamespacedKey key(String value){
        return new NamespacedKey(SleepyChronicles.getInstance(),value);
    }

    @NotNull
    private static PersistentDataContainer getDataContainer(PersistentDataHolder holder){
        return holder.getPersistentDataContainer();
    }

}
