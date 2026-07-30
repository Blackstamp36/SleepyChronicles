package org.blackstamp.sleepychronicles.api.mobs.config;

import lombok.Builder;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.blackstamp.sleepychronicles.api.mobs.MovementType;
import org.blackstamp.sleepychronicles.api.mobs.attacks.SleepyAttack;

import java.util.Map;

@Builder
public record MobConfig(
    String name,
    String color,
    SoundEvent hurtSound,
    SoundEvent deathSound,
    MovementType movementType,
    SleepyAttack<Mob> attack,
    Map<Holder<Attribute>, Double> attributes
    ) implements BaseConfig{}
