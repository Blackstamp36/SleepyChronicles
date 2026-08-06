package org.blackstamp.sleepychronicles.api.mobs;

import lombok.Getter;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.api.mobs.config.MobConfig;
import org.blackstamp.sleepychronicles.api.mobs.movement.MovementType;
import org.blackstamp.sleepychronicles.api.text.SleepyText;
import org.blackstamp.sleepychronicles.game.mobs.custom.vanilla.VanillaZombie;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public enum SleepyMobs {
    TEST_MOB(level ->
            new VanillaZombie(level,MobConfig.builder()
                    .displayName(new SleepyText("Test Mob", SleepyPalette.VANILLA,0))
                    .hurtSound(SoundEvents.BEE_HURT).deathSound(SoundEvents.BEE_DEATH)
                    .drops(List.of(
                            new ItemStack(Items.BEDROCK,ThreadLocalRandom.current().nextInt(1,3))
                    ))
                    .movementType(MovementType.GROUND)
                    .attack(null)
                    .attributes(Map.of(
                            Attributes.MAX_HEALTH, 20.0D,
                            Attributes.ATTACK_KNOCKBACK, 1.5D
                    ))
                    .build()));

    @Getter private final String id;
    private final Function<Level,Mob> mob;
    private static final Map<String, Function<Level,Mob>> REGISTRY = new HashMap<>();

    static{
        for(SleepyMobs type : values()){ REGISTRY.put(type.name().toLowerCase(), type.mob); }
    }

    SleepyMobs(Function<Level,Mob> mob){
        this.id = this.name().toLowerCase();
        this.mob = mob;
    }

    public static Function<Level,Mob> getMob(String id){
        return REGISTRY.get(id.toLowerCase());
    }
}
