package org.blackstamp.sleepychronicles.api.world;

import org.blackstamp.sleepychronicles.game.world.dimensions.AftermathBiomeProvider;
import org.blackstamp.sleepychronicles.game.world.dimensions.AftermathChunkGenerator;
import org.bukkit.*;
import org.bukkit.WorldType;

public class WorldManager {
    private WorldManager(){}

    public static void applyDefaultWorldRules(World world) {
        if(world == null) return;

        world.setGameRule(GameRule.DO_MOB_SPAWNING, true);
        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setTime(13000);
        world.setStorm(false);
        world.setThundering(false);

        WorldBorder border = world.getWorldBorder();
        border.setCenter(0.0, 0.0);
        border.setSize(10000.0);
    }

    public static void createAftermathDimension() {
        WorldCreator worldCreator = WorldCreator.name(org.blackstamp.sleepychronicles.api.world.WorldType.AFTERMATH.getWorldName())
                .environment(World.Environment.NORMAL)
                .type(WorldType.NORMAL)
                .biomeProvider(new AftermathBiomeProvider())
                .generator(new AftermathChunkGenerator());

        applyDefaultWorldRules(worldCreator.createWorld());
    }
}
