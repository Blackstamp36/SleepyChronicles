package org.blackstamp.sleepychronicles.api.mobs.config;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.ItemStack;
import org.blackstamp.sleepychronicles.api.mobs.MovementType;
import org.blackstamp.sleepychronicles.api.mobs.attacks.SleepyAttack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public interface BaseConfig {
    String name();
    @Nullable String color();
    SoundEvent hurtSound();
    SoundEvent deathSound();
    @Nullable List<ItemStack> drops();
    MovementType movementType();
    @Nullable SleepyAttack<Mob> attack();
    Map<Holder<Attribute>, Double> attributes();
}
