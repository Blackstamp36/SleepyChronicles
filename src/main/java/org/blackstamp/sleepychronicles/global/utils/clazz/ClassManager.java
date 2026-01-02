package org.blackstamp.sleepychronicles.global.utils.clazz;

import net.minecraft.world.entity.EntityType;

public class ClassManager {

    public EntityType<?> getEntityTypeForClass(Class<?> entityClass) {
        return switch(entityClass.getSimpleName()) {
            case "Creeper" -> EntityType.CREEPER;
            case "Spider" -> EntityType.SPIDER;
            case "Fox" -> EntityType.FOX;
            case "Slime" -> EntityType.SLIME;
            case "Creaking" -> EntityType.CREAKING;
            case "WitherBoss" -> EntityType.WITHER;
            case "IronGolem" -> EntityType.IRON_GOLEM;
            case "Ghast" -> EntityType.GHAST;
            case "Zombie" -> EntityType.ZOMBIE;
            case "Phantom" -> EntityType.PHANTOM;
            case "Skeleton" -> EntityType.SKELETON;
            case "Bogged" -> EntityType.BOGGED;
            case "Vex" -> EntityType.VEX;
            case "EnderMan" -> EntityType.ENDERMAN;
            case "Endermite" -> EntityType.ENDERMITE;
            case "Evoker" -> EntityType.EVOKER;
            case "Llama" -> EntityType.LLAMA;
            default -> throw new RuntimeException("No mapping found for entity: " + entityClass.getName());
        };
    }
}