package org.blackstamp.sleepychronicles.api.dungeon;

public enum DungeonType {

    TEST_DUNGEON(1000);

    private final double radius;

    DungeonType(double radius){
        this.radius = radius;
    }
}