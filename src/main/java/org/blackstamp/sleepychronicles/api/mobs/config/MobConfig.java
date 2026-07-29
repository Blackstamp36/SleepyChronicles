package org.blackstamp.sleepychronicles.api.mobs.config;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.blackstamp.sleepychronicles.api.mobs.MovementType;
import org.blackstamp.sleepychronicles.api.mobs.SleepyMob;
import org.blackstamp.sleepychronicles.api.mobs.attacks.SleepyAttack;

import java.util.Map;

public record MobConfig(
    String name,
    String color,
    SoundEvent hurtSound,
    SoundEvent deathSound,
    MovementType movementType,
    SleepyAttack<SleepyMob> attack,
    Map<Holder<Attribute>, Double> attributes
    ) implements BaseConfig{}
