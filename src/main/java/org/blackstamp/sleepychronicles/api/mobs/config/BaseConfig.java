package org.blackstamp.sleepychronicles.api.mobs.config;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.blackstamp.sleepychronicles.api.mobs.MovementType;
import org.blackstamp.sleepychronicles.api.mobs.SleepyMob;
import org.blackstamp.sleepychronicles.api.mobs.attacks.SleepyAttack;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface BaseConfig {
    String name();
    @Nullable String color();
    SoundEvent hurtSound();
    SoundEvent deathSound();
    MovementType movementType();
    @Nullable SleepyAttack<SleepyMob> attack();
    Map<Holder<Attribute>, Double> attributes();
}
