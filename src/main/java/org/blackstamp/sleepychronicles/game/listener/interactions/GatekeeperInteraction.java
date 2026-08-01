package org.blackstamp.sleepychronicles.game.listener.interactions;

import org.blackstamp.sleepychronicles.api.inventory.menu.dungeon.DungeonSelector;
import org.blackstamp.sleepychronicles.api.mobs.npc.MobInteraction;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class GatekeeperInteraction implements MobInteraction {
    public void onInteraction(PlayerInteractEntityEvent e){
        Player p = e.getPlayer();
        DungeonSelector menu = new DungeonSelector(p, null);

        menu.open(p);
    }
}
