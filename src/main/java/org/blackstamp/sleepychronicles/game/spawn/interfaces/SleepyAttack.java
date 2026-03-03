package org.blackstamp.sleepychronicles.game.spawn.interfaces;

import org.bukkit.event.entity.EntityDamageByEntityEvent;

public interface SleepyAttack {
    void handleAttack(EntityDamageByEntityEvent e);
}