package org.blackstamp.sleepyChronicles.dimension;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class AftermathTreePopulator extends BlockPopulator {

    @Override
    public void populate(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull LimitedRegion limitedRegion) {
        if (!worldInfo.getName().equals("world_aftermath")) return;

        int treesToPlace = 2 + random.nextInt(4);

        for (int i = 0; i < treesToPlace; i++) {
            int x = chunkX * 16 + random.nextInt(16);
            int z = chunkZ * 16 + random.nextInt(16);

            int y = findTopGrassBlock(limitedRegion, x, z);
            if (y != Integer.MIN_VALUE) {

                if (random.nextInt(0,101) <= 90) {
                    Location treeLoc = new Location(null, x, y + 1, z);
                    generateTree(limitedRegion, treeLoc, random);
                }
            }
        }
    }

    private int findTopGrassBlock(@NotNull LimitedRegion region, int x, int z) {
        for (int y = region.getHighestBlockYAt(x, z) - 1; y >= region.getWorld().getMinHeight(); y--) {
            if (region.getType(x, y, z) == Material.GRASS_BLOCK) {
                if (region.getType(x, y + 1, z) == Material.AIR) {
                    return y;
                }
            }
        }
        return Integer.MIN_VALUE;
    }

    private void generateTree(@NotNull LimitedRegion region, Location location, Random r) {

        TreeType[] treeTypes = {
                TreeType.PALE_OAK,
        };
        TreeType treeType = treeTypes[r.nextInt(treeTypes.length)];

        region.generateTree(location, r, TreeType.PALE_OAK);
    }
}
