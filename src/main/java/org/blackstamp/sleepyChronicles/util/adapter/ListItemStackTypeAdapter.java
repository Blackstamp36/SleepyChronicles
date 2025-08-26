package org.blackstamp.sleepyChronicles.util.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListItemStackTypeAdapter extends TypeAdapter<List<ItemStack>> {

    private final ItemStackTypeAdapter itemStackAdapter = new ItemStackTypeAdapter();

    @Override
    public void write(JsonWriter out, List<ItemStack> list) throws IOException {
        if (list == null) {
            out.value("");
            return;
        }
        out.beginArray();
        for (ItemStack item : list) {
            itemStackAdapter.write(out, item); // Delegate to the single ItemStack adapter
        }
        out.endArray();
    }

    @Override
    public List<ItemStack> read(JsonReader in) throws IOException {
        if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        List<ItemStack> list = new ArrayList<>();
        in.beginArray();
        while (in.hasNext()) {
            ItemStack item = itemStackAdapter.read(in); // Delegate to the single ItemStack adapter
            list.add(item);
        }
        in.endArray();
        return list;
    }
}