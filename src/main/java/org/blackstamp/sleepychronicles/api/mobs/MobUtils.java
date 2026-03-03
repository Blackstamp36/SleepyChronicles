package org.blackstamp.sleepychronicles.api.mobs;

import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MobUtils {

    private static final HashMap<String, Constructor<? extends SleepyMob>> MOB_CONSTRUCTORS = new HashMap<>();
    public static void initializeMobConstructors(){
        Reflections reflections = new Reflections("org.blackstamp.sleepychronicles.game.mobs.custom");

        for(Class<? extends SleepyMob> clazz : reflections.getSubTypesOf(SleepyMob.class)){
            try{
                Constructor<? extends SleepyMob> constructor = clazz.getConstructor(Level.class);
                final String name = clazz.getSimpleName();

                MOB_CONSTRUCTORS.put(name,constructor);
            }catch(Exception e){
                throw new RuntimeException();
            }
        }
    }

    public static @Nullable SleepyMob instantiateMob(String mobName, Level level){
        Constructor<? extends SleepyMob> constructor = MOB_CONSTRUCTORS.get(mobName);

        if(constructor == null) return null;
        try{ return constructor.newInstance(level);
        }catch(Exception e){
            throw new RuntimeException();
        }
    }

    public static List<String> getMobNames(){
        Reflections reflections = new Reflections("org.blackstamp.sleepychronicles");
        Set<Class<? extends SleepyMob>> mobClasses = reflections.getSubTypesOf(SleepyMob.class);
        List<String> mobNames = new ArrayList<>();

        for(Class<? extends SleepyMob> clazz : mobClasses) mobNames.add(clazz.getSimpleName());

        return mobNames;
    }

}
