package org.blackstamp.sleepychronicles.game.spawn;

import org.bukkit.event.entity.CreatureSpawnEvent;

public interface SpawnProcessor {
    void process(CreatureSpawnEvent e, int day);
}