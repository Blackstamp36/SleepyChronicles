package org.blackstamp.sleepychronicles.game.mobs.custom.bosses;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.mobs.MovementType;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossConfig;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;
import org.blackstamp.sleepychronicles.api.text.TextFormatter;
import org.blackstamp.sleepychronicles.game.mobs.goals.boss.DarknessEmperorAttacks;

public class DarknessEmperor extends BossMob {
    private final static int MAX_HEALTH = 1000;
    private final static float SCALE = 0.55F;
    private final static float KNOCKBACK_RESISTANCE = 1F;

    private final static BossConfig BOSS_CONFIG = new BossConfig(
            "Darkness Emperor",
            "#5e17a1",
            0.75D,
            10, 10, 10,
            10, 10,
            10, 10, 10,
            SoundEvent.createVariableRangeEvent(ResourceLocation.parse("THEME_KEY")), 10, // THEME_KEY is just an example
            new ServerBossEvent(TextFormatter.toComponent("Alward, Senior of Darkness","#9d78bc"),
                    BossEvent.BossBarColor.PURPLE,
                    BossEvent.BossBarOverlay.NOTCHED_6)
            );

    public DarknessEmperor(Level level){
        super(EntityType.GHAST, level, BOSS_CONFIG.name(), BOSS_CONFIG.color(),
                MovementType.FLIGHT, DarknessEmperorAttacks.values(),
                BOSS_CONFIG
        );
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, MAX_HEALTH)
                .add(Attributes.SCALE,SCALE)
                .add(Attributes.KNOCKBACK_RESISTANCE,KNOCKBACK_RESISTANCE);
    }
}