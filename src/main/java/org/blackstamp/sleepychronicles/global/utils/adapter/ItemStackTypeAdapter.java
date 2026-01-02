package org.blackstamp.sleepychronicles.global.utils.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ItemStackTypeAdapter extends TypeAdapter<ItemStack> {

    @Override
    public void write(JsonWriter out, ItemStack item) throws IOException {
        if (item == null) {
            out.value("");
            return;
        }
        // Convert the ItemStack to a Base64 string
        out.value(itemStackToBase64(item));
    }

    @Override
    public ItemStack read(JsonReader in) throws IOException {
        // Check if the next value is null first
        if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        // Read the Base64 string and convert it back to an ItemStack
        String data = in.nextString();
        return itemStackFromBase64(data);
    }

    // Helper method to convert an ItemStack to a Base64 string.
    private String itemStackToBase64(ItemStack item) throws IOException {
        if (item == null) {
            return "";
        }
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream)) {

            dataOutput.writeObject(item);
            return Base64Coder.encodeLines(outputStream.toByteArray());
        }
    }

    // Helper method to convert a Base64 string back to an ItemStack.
    private ItemStack itemStackFromBase64(String data) throws IOException {
        if (data == null || data.isEmpty()) {
            return null;
        }
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
             BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {

            return (ItemStack) dataInput.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }
}