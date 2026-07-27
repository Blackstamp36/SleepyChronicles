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
    double retreatRadius,
    double strafeRadius,
    double aggroRadius,
    double maxDistance,
    double minDistance,
    SoundEvent soundEvent,
    int themeTicks,
    ServerBossEvent bar
    ){}
