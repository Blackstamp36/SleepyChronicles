package org.blackstamp.sleepychronicles.api.dungeon;

public enum DungeonTypes {

    TEST_DUNGEON(1000);

    private final double radius;

    DungeonTypes(double radius){
        this.radius = radius;
    }
}
