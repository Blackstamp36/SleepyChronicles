package org.blackstamp.sleepychronicles.api.mobs.boss;

import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.sounds.SoundEvent;

public record BossConfig(
    String name,
    String color,
    double speed,
    int evadeCooldown,
    int evadingTicks,
    int evadeRadius,
    float decay,
    int decayTicks,
    int retreatRadius,
    int aggroRadius,
    int maxDistance,
    int minDistance,
    SoundEvent soundEvent,
    int themeTicks,
    ServerBossEvent bar
    ){}
