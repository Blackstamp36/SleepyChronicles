package org.blackstamp.sleepychronicles.api.mobs;

import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;
import org.blackstamp.sleepychronicles.api.mobs.config.BossConfig;
import org.blackstamp.sleepychronicles.api.mobs.config.MobConfig;
import org.blackstamp.sleepychronicles.api.mobs.movement.MovementType;
import org.blackstamp.sleepychronicles.api.text.TextFormatter;
import org.blackstamp.sleepychronicles.game.mobs.custom.bosses.attacks.DarknessEmperorAttacks;
import org.blackstamp.sleepychronicles.game.mobs.custom.vanilla.VanillaZombie;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public enum SleepyMobs {
    DARKNESS_EMPEROR("darkness_emperor", level ->
            new BossMob(EntityType.GHAST,level,BossConfig.builder()
                    .name("Senior of Darkness").color("#5e17a1")
                    .hurtSound(SoundEvents.ALLAY_HURT).deathSound(SoundEvents.WARDEN_DEATH)
                    .drops(null)
                    .movementType(MovementType.FLIGHT)
                    .attributes(Map.of(
                            Attributes.MAX_HEALTH, 1000.0D,
                            Attributes.SCALE, 0.35D,
                            Attributes.KNOCKBACK_RESISTANCE, 0.35D
                    ))
                    .baseSpeed(1.25D)
                    .bossAttacks(DarknessEmperorAttacks.values())
                    .dodgeCooldown(50).dodgingTicks(10).dodgeDetectionRadius(16).dodgeSpeed(10.5D)
                    .decay(0.75F).decayTicks(20)
                    .retreatRadius(10).strafeRadius(5).aggroRadius(30)
                    .maxDistance(8).minDistance(16)
                    .themeSound(SoundEvent.createVariableRangeEvent(ResourceLocation.parse("theme_key")))
                    .themeTicks(0)
                    .bar(
                            new ServerBossEvent(TextFormatter.toComponent("Alward, Senior of Darkness","#9d78bc"),
                                    BossEvent.BossBarColor.PURPLE,
                                    BossEvent.BossBarOverlay.NOTCHED_6
                            ))
                    .build())),
    TEST_MOB("test_mob",level ->
            new VanillaZombie(level,MobConfig.builder()
                    .name("Test Mob").color(null)
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
    private static final Random RANDOM = ThreadLocalRandom.current();

    static {
        for(SleepyMobs type : values()){ REGISTRY.put(type.id, type.mob); }
    }

    SleepyMobs(String id, Function<Level,Mob> mob){
        this.id = id;
        this.mob = mob;
    }

    public static Function<Level,Mob> getMob(String id){ return REGISTRY.get(id.toLowerCase()); }

    public static Set<String> getIDs(){ return REGISTRY.keySet(); }
}