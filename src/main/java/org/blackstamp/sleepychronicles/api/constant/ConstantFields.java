package org.blackstamp.sleepychronicles.api.constant;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.EnumSet;
import java.util.Set;

public class ConstantFields {
    public static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    public static final String PARRY_KEY = "parry";

    public static final Set<EntityDamageEvent.DamageCause> PARRYABLE_CAUSES = EnumSet.of(
            EntityDamageEvent.DamageCause.ENTITY_ATTACK,
            EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK,
            EntityDamageEvent.DamageCause.PROJECTILE
    );

    public static final long ONE_DAY = 86400000L;
    public static final long ONE_HOUR = 3600000L;
    public static final long ONE_MINUTE = 60000L;
    public static final long ONE_SECOND = 1000L;
}
