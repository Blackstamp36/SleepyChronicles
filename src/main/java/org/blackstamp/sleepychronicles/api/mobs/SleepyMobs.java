package org.blackstamp.sleepychronicles.api.mobs;

import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;
import org.blackstamp.sleepychronicles.api.mobs.config.BossConfig;
import org.blackstamp.sleepychronicles.api.mobs.config.MobConfig;
import org.blackstamp.sleepychronicles.api.text.TextFormatter;
import org.blackstamp.sleepychronicles.game.mobs.custom.bosses.attacks.DarknessEmperorAttacks;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public enum SleepyMobs {

    DARKNESS_EMPEROR("darkness_emperor", level -> new BossMob(EntityType.GHAST,level,new BossConfig(
            "Senior of Darkness","#5e17a1",
            SoundEvents.ALLAY_HURT,SoundEvents.WARDEN_DEATH,
            MovementType.FLIGHT,
            null,
            Map.of(
                    Attributes.MAX_HEALTH, 20.0D,
                    Attributes.SCALE, 0.35D,
                    Attributes.KNOCKBACK_RESISTANCE, 1.0D
            ),
            1.25D,
            DarknessEmperorAttacks.values(),
            60,4,20,10.25D,
            0.75F,40,
            16,8,30,
            30, 30,
            SoundEvent.createVariableRangeEvent(ResourceLocation.parse("theme_key")),
            1,
            new ServerBossEvent(TextFormatter.toComponent("Alward, Senior of Darkness","#9d78bc"),
                    BossEvent.BossBarColor.PURPLE,
                    BossEvent.BossBarOverlay.NOTCHED_6)
            )
    )),
    TEST_MOB("test_mob",level -> new SleepyMob(EntityType.ZOMBIE,level,new MobConfig(
                    "Test Mob",null,
                    SoundEvents.BEE_HURT,SoundEvents.BEE_DEATH,
                    MovementType.GROUND,
                    null,
                    Map.of(
                            Attributes.MAX_HEALTH, 20.0
                    )
            )
    ));

    @Getter private final String id;
    private final Function<Level, SleepyMob> mob;
    private static final Map<String, Function<Level, SleepyMob>> REGISTRY = new HashMap<>();

    static {
        for(SleepyMobs type : values()){ REGISTRY.put(type.id, type.mob); }
    }

    SleepyMobs(String id, Function<Level, SleepyMob> mob){
        this.id = id;
        this.mob = mob;
    }

    public static Function<Level, SleepyMob> getMob(String id){ return REGISTRY.get(id.toLowerCase()); }

    public static Set<String> getIDs(){ return REGISTRY.keySet(); }
}