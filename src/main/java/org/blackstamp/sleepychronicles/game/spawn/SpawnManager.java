package org.blackstamp.sleepychronicles.game.spawn;

import lombok.Getter;
import org.blackstamp.sleepychronicles.api.data.days.DayManager;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SpawnManager {

    @Getter private static SpawnManager instance;
    private static Map<org.bukkit.entity.EntityType, SpawnProcessor> processors;

    public SpawnManager(){
        instance = this;
        processors = new HashMap<>();
    }

    public void spawn(CreatureSpawnEvent e){
        if(e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) return;

        SpawnProcessor mob = processors.get(e.getEntity().getType());

        if(mob == null) return;

        mob.process(e,DayManager.getInstance().getDay());
    }

    public void register(){
        String basePackage = "org.blackstamp.sleepychronicles.game.spawn.processors";
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(MobProcessor.class);

        for(Class<?> clazz : annotatedClasses){
            try{
            if(!SpawnProcessor.class.isAssignableFrom(clazz)) continue;

            MobProcessor mob = clazz.getAnnotation(MobProcessor.class);
            SpawnProcessor instance = (SpawnProcessor) clazz.getDeclaredConstructor().newInstance();

            for(org.bukkit.entity.EntityType type : mob.value()) processors.put(type, instance);

        }catch(Exception e){ throw new RuntimeException(); }
        }
    }
}