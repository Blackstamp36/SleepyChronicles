package org.blackstamp.sleepychronicles.api.mobs.boss;

import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.api.mobs.config.BossConfig;
import org.blackstamp.sleepychronicles.api.mobs.movement.MovementType;
import org.blackstamp.sleepychronicles.api.text.SleepyText;
import org.blackstamp.sleepychronicles.api.text.TextFormatter;
import org.blackstamp.sleepychronicles.game.mobs.custom.bosses.attacks.DarknessEmperorAttacks;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public enum SleepyBosses {
    DARKNESS_EMPEROR(level ->
            new BossMob(EntityType.GHAST,level,BossConfig.builder()
                    .displayName(new SleepyText("Senior of Darkness", SleepyPalette.DARKNESS,0))
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
                            new ServerBossEvent(TextFormatter.toComponent("Alward, Senior of Darkness", SleepyPalette.DARKNESS,1),
                                    BossEvent.BossBarColor.PURPLE,
                                    BossEvent.BossBarOverlay.NOTCHED_6
                            ))
                    .build()
            ));

    @Getter private final String id;
    @Getter private final Function<Level,Mob> mob;
    private static final Map<String, Function<Level,Mob>> REGISTRY = new HashMap<>();

    static {
        for(SleepyBosses type : values()){
            REGISTRY.put(type.id, type.mob);
        }
    }

    SleepyBosses(Function<Level,Mob> mob){
        this.id = this.name().toLowerCase();
        this.mob = mob;
    }

    public static Function<Level,Mob> getBoss(String id){
        return REGISTRY.get(id.toLowerCase());
    }
}
