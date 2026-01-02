package org.blackstamp.sleepychronicles.game.world.dimensions;

import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class AftermathBiomeProvider extends BiomeProvider {

    private final Biome biome;

    public AftermathBiomeProvider() {
        this.biome = Biome.PALE_GARDEN;
    }

    @NotNull
    @Override
    public Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
        return biome;
    }

    @NotNull
    @Override
    public List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
        return Collections.singletonList(biome);
    }
}