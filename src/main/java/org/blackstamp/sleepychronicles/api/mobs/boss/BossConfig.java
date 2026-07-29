package org.blackstamp.sleepychronicles.api.mobs.boss;

import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public record BossConfig(
    String name,
    String color,
    double speed,
    int dodgeCooldown,
    int dodgingTicks,
    int dodgeDetectionRadius,
    double dodgeSpeed,
    float decay,
    int decayTicks,
    double retreatRadius,
    double strafeRadius,
    double aggroRadius,
    double maxDistance,
    double minDistance,
    SoundEvent hurtSound,
    SoundEvent deathSound,
    SoundEvent soundEvent,
    int themeTicks,
    ServerBossEvent bar
    ){}
