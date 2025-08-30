package org.blackstamp.sleepyChronicles.dimension;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class AftermathChunkGenerator extends ChunkGenerator {

    private final SimplexNoise centralIslandNoise;
    private final SimplexNoise outerIslandsNoise;
    private final SimplexNoise detailNoise;

    public AftermathChunkGenerator() {
        long seed = 12345L;

        RandomSource randomSource = RandomSource.create(seed);

        this.centralIslandNoise = new SimplexNoise(randomSource);
        this.outerIslandsNoise = new SimplexNoise(randomSource.fork());
        this.detailNoise = new SimplexNoise(randomSource.fork());
    }

    @Override
    public void generateNoise(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = chunkX * 16 + x;
                int worldZ = chunkZ * 16 + z;

                double scaledX = worldX * 0.01;
                double scaledZ = worldZ * 0.01;

                double centralNoise = this.centralIslandNoise.getValue(scaledX, 0.0, scaledZ);
                double outerNoise = this.outerIslandsNoise.getValue(scaledX * 2, 0.0, scaledZ * 2);

                double distanceFromCenter = Math.sqrt(worldX * worldX + worldZ * worldZ);
                double centralGradient = Math.max(0, 1.0 - (distanceFromCenter / 1000.0));
                double centralIslandValue = centralNoise * centralGradient;

                double outerIslandsValue = (distanceFromCenter > 500) ? outerNoise : -1.0;

                double combinedRawNoise = Math.max(centralIslandValue, outerIslandsValue);

                combinedRawNoise = Math.max(-1.0, Math.min(1.0, combinedRawNoise));

                double normalizedNoise = (combinedRawNoise + 1.0) / 2.0;

                if (normalizedNoise > 0.6) {
                    generateLandColumn(worldInfo, chunkData, x, z, normalizedNoise, worldX, worldZ);

                } else {
                    generateVoidColumn(worldInfo, chunkData, x, z);
                }
            }
        }
    }

    private void generateLandColumn(@NotNull WorldInfo worldInfo, @NotNull ChunkData chunkData, int x, int z, double noiseValue, int worldX, int worldZ) {
        Random r = new Random();
        int surfaceHeight = 56 + (int) (noiseValue * 10);

        double detailNoise = this.detailNoise.getValue(worldX * 0.1, worldZ * 0.1, 0.0);

        Material surfaceMaterial;
        double materialChoice = (detailNoise + 1.0) / 2.0;

        if (materialChoice < 0.6) {
            surfaceMaterial = Material.GRASS_BLOCK;
        } else if (materialChoice < 0.8) {
            surfaceMaterial = Material.PALE_MOSS_BLOCK;
        } else {
            surfaceMaterial = Material.ROOTED_DIRT;
        }

        int actualSurfaceHeight = surfaceHeight + (int)(detailNoise * 2);

        int dirtLayers = 6 + (int)((detailNoise + 1.0) * 1.5);
        for (int y = actualSurfaceHeight - dirtLayers; y < actualSurfaceHeight; y++) {
            if(r.nextInt(5001) < 5000){
            chunkData.setBlock(x, y, z, Material.DIRT);
        } else {
                chunkData.setBlock(x, y, z, Material.CHISELED_RESIN_BRICKS);
            }
        }

        chunkData.setBlock(x, actualSurfaceHeight, z, surfaceMaterial);

        if (r.nextDouble() < 0.05) {
            placeSurfaceDecoration(chunkData, x, actualSurfaceHeight + 1, z, r);
        }
    }

    private void placeSurfaceDecoration(ChunkData chunkData, int x, int y, int z, Random r) {
        Material[] decorations = {
                Material.SHORT_GRASS,
                Material.FERN,
                Material.PALE_MOSS_CARPET,
                Material.DEAD_BUSH
        };

        chunkData.setBlock(x, y, z, decorations[r.nextInt(decorations.length)]);
    }

    private void generateVoidColumn(@NotNull WorldInfo worldInfo, @NotNull ChunkData chunkData, int x, int z) {
        for (int y = worldInfo.getMinHeight(); y < worldInfo.getMaxHeight(); y++) {
            chunkData.setBlock(x, y, z, Material.AIR);
        }
    }

    @NotNull
    @Override
    public List<BlockPopulator> getDefaultPopulators(@NotNull World world) {
        return List.of(new AftermathTreePopulator());
    }
}
