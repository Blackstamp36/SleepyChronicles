package org.blackstamp.sleepychronicles.api.mobs.npc;

import org.bukkit.event.player.PlayerInteractEntityEvent;

public interface MobInteraction {
    void onInteraction(PlayerInteractEntityEvent e);
}
