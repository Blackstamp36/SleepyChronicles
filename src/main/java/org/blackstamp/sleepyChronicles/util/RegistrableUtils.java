package org.blackstamp.sleepyChronicles.util;

import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.reflections.Reflections;

public class RegistrableUtils {
    public static void registerListeners() {
        Reflections reflections = new Reflections("org.blackstamp.sleepyChronicles");

        for (Class<?> clazz : reflections.getTypesAnnotatedWith(Registrable.class)) {
            try {
                if (!(clazz.getDeclaredConstructor().newInstance() instanceof Listener listener)) continue;
                Bukkit.getServer().getPluginManager().registerEvents(listener, sleepyChronicles.getter());
            } catch (Exception exception) {
                exception.fillInStackTrace();
            }
        }
    }

}