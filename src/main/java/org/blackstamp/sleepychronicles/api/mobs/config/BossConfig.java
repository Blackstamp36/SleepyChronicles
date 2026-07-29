package org.blackstamp.sleepychronicles.api.mobs.config;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.blackstamp.sleepychronicles.api.mobs.MovementType;
import org.blackstamp.sleepychronicles.api.mobs.SleepyMob;
import org.blackstamp.sleepychronicles.api.mobs.attacks.BossAttack;
import org.blackstamp.sleepychronicles.api.mobs.attacks.SleepyAttack;

import java.util.Map;

public record BossConfig(
    String name,
    String color,
    SoundEvent hurtSound,
    SoundEvent deathSound,
    MovementType movementType,
    SleepyAttack<SleepyMob> attack,
    Map<Holder<Attribute>, Double> attributes,
    double baseSpeed,
    BossAttack[] bossAttacks,
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
    SoundEvent soundEvent,
    int themeTicks,
    ServerBossEvent bar
    ) implements BaseConfig{}
