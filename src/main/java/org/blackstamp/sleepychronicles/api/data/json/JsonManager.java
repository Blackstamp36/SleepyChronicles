package org.blackstamp.sleepychronicles.api.data.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class JsonManager<T> {
    private final Gson gsonBuilder = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();
    private final File file;
    private final File parent;
    private final Class<T> type;

    public JsonManager(Plugin plugin, String filename, Class<T> type){
        this.file = new File(plugin.getDataFolder(), filename + ".json");
        this.type = type;
        this.parent = file.getParentFile();
    }

    public void save(T data){
        if(!parent.exists()) parent.mkdirs();

        try(BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)){
            gsonBuilder.toJson(data, writer);
            writer.flush();
        }catch(Exception e){
            throw new RuntimeException();
        }
    }

    public T get(){
        if(!(file.exists())) return null;

        try(BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)){
            return gsonBuilder.fromJson(reader, type);
        }catch(Exception e){
            throw new RuntimeException();
        }
    }

    public T getOrCreate(){
        T data = get();

        if(!(data == null)) return data;
        try{
            data = type.getDeclaredConstructor().newInstance();
            save(data);
            return data;
        }catch(Exception e){
            throw new RuntimeException();
        }
    }
}