package org.blackstamp.sleepyChronicles.util.nms;

import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.summonableMob;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NMSEntityRegistry {

    private final Map<String, Class<?>> entityClassMap = new HashMap<>();

    public void scanNMSClasses() {
        String basePackage = "org.blackstamp.sleepyChronicles.nms.v1_21_5_R01";
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(NMSEntity.class);

        for (Class<?> clazz : annotatedClasses){
            String entityKey = clazz.getSimpleName().toLowerCase();

            if(Arrays.asList(clazz.getInterfaces()).contains(summonableMob.class)
                    || entityClassMap.containsKey(entityKey)) continue;

            entityClassMap.put(entityKey, clazz);
        }
    }

    public Class<?> getNMSClass(String name) {
        return entityClassMap.get(name.toLowerCase());
    }

    public Map<String, Class<?>> getNMSEntitiesMap() {
        return Map.copyOf(entityClassMap);
    }
}
