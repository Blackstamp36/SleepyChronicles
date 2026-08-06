package org.blackstamp.sleepychronicles.api.mobs.npc;

import lombok.Getter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.api.text.TextFormatter;
import org.blackstamp.sleepychronicles.game.listener.interactions.GatekeeperInteraction;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public enum SleepyNPCs {
    VEIL_GUARDIAN(level -> {
        Villager npc = new Villager(EntityType.VILLAGER, level); // "#ebc934"
        npc.setCustomName(TextFormatter.toComponent("Veil Guardian", SleepyPalette.VANILLA));
        npc.setNoAi(true);
        npc.setInvulnerable(true);
        npc.setSpeed(0);

        return npc;
    }, new GatekeeperInteraction()
    );

    @Getter private final String id;
    @Getter private final Function<Level,Mob> mob;
    @Getter private final MobInteraction interaction;

    private static final Map<String, SleepyNPCs> REGISTRY = new HashMap<>();

    static {
        for(SleepyNPCs type : values()){
            REGISTRY.put(type.name().toLowerCase(), type);
        }
    }

    SleepyNPCs(Function<Level, Mob> mob, MobInteraction interaction){
        this.id = this.name().toLowerCase();
        this.mob = mob;
        this.interaction = interaction;
    }

    public static Function<Level,Mob> getNPC(String id){
        SleepyNPCs npc = REGISTRY.get(id);

        return npc != null ? npc.getMob() : null;
    }

    public static MobInteraction getInteraction(String id){
        SleepyNPCs npc = REGISTRY.get(id);

        return npc != null ? npc.getInteraction() : null;
    }
}