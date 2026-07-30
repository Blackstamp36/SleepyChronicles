package org.blackstamp.sleepychronicles.api.item.trinket;

import org.bukkit.event.entity.EntityDamageEvent;

public interface TrinketAbility {
    default void onDamageTaken(EntityDamageEvent e){}
}
