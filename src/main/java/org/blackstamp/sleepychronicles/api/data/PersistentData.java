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
    public static <T,Z> Z get(PersistentDataHolder holder, NamespacedKey key, PersistentDataType<T,Z> type){
        if(holder == null) return null;

        return PersistentData.getDataContainer(holder).get(key, type);
    }

    public static <T,Z> void set(PersistentDataHolder holder, NamespacedKey key, PersistentDataType<T, Z> type, Z value){
        if(holder == null) return;

        PersistentData.getDataContainer(holder).set(key, type, value);
    }

    public static <T,Z> boolean equals(PersistentDataHolder holder, NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
        if(!PersistentData.has(holder, key)) return false;

        return Objects.equals(get(holder, key, type), value);
    }

    public static boolean has(PersistentDataHolder holder, NamespacedKey key){
        if(holder == null) return false;

        return holder.getPersistentDataContainer().has(key);
    }

    public static void remove(PersistentDataHolder holder, NamespacedKey key){
        if(holder == null) return;

        getDataContainer(holder).remove(key);
    }

    @NotNull
    public static NamespacedKey key(String value){
        return new NamespacedKey(SleepyChronicles.getInstance(),value);
    }

    @NotNull
    public static NamespacedKey key(boolean value){
        return new NamespacedKey(SleepyChronicles.getInstance(), String.valueOf(value));
    }

    @NotNull
    private static PersistentDataContainer getDataContainer(PersistentDataHolder holder){
        return holder.getPersistentDataContainer();
    }

}
