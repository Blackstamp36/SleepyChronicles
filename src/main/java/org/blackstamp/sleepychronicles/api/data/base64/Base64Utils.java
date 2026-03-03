package org.blackstamp.sleepychronicles.api.data.base64;

import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.util.Base64;

public class Base64Utils {

    @SuppressWarnings("deprecation")
    public static String toBase64(Object object){
        try{
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream objectStream = new BukkitObjectOutputStream(byteStream);

            objectStream.writeObject(object);
            objectStream.flush();

            byte[] byteArray = byteStream.toByteArray();

            return Base64.getUrlEncoder().encodeToString(byteArray);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("deprecation")
    public static Object fromBase64(String value){
        try{
            byte[] byteData = Base64.getUrlDecoder().decode(value);
            var byteStream = new ByteArrayInputStream(byteData);
            var objectStream = new BukkitObjectInputStream(byteStream);

            return objectStream.readObject();
        }catch (IOException | ClassNotFoundException e){
            throw new RuntimeException("An exception has occurred: " + e);
        }
    }
}
