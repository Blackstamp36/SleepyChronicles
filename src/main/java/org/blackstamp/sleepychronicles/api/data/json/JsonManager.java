package org.blackstamp.sleepychronicles.api.data.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class JsonManager<T> {
    private final Gson gsonBuilder = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();
    private final File file;
    private final Class<T> type;

    public JsonManager(Plugin plugin, String filename, Class<T> type){
        this.file = new File(plugin.getDataFolder(), filename);
        this.type = type;
    }

    public void save(){

    }

    public void get(){

    }
}
