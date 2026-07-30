package org.blackstamp.sleepychronicles.api.item;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public interface ItemAbility {
    default void onInteract(PlayerInteractEvent e){}

    default void onArmorHit(EntityDamageEvent e, Player p){}
    default void onArmorTick(Player p){}
}
