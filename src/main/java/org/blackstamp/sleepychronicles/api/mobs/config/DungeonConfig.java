package org.blackstamp.sleepychronicles.api.mobs.config;

import lombok.Builder;
import org.blackstamp.sleepychronicles.api.item.ItemBuilder;
import org.bukkit.Location;

import java.util.function.Supplier;

@Builder
public record DungeonConfig(
        String bossId,
        String schematic,
        Location center,
        int maxPlayers,
        double radius,
        int timeLimitSeconds,
        Supplier<ItemBuilder> icon
){}
