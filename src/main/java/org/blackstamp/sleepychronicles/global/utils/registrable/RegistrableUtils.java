package org.blackstamp.sleepychronicles.global.utils.registrable;

import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.reflections.Reflections;

public class RegistrableUtils {
    public static void registerListeners() {
        Reflections reflections = new Reflections("org.blackstamp.SleepyChronicles");

        for(Class<?> clazz : reflections.getTypesAnnotatedWith(Registrable.class)) {
            try {
                if (!(clazz.getDeclaredConstructor().newInstance() instanceof Listener listener)) continue;
                Bukkit.getServer().getPluginManager().registerEvents(listener, SleepyChronicles.getInstance());
            } catch (Exception exception) {
                exception.fillInStackTrace();
            }
        }
    }

}