package org.blackstamp.sleepychronicles.api.item;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public interface ItemAbility {
    void onInteract(PlayerInteractEvent e);
    void onHit(EntityDamageByEntityEvent e);

    void onArmorHit(EntityDamageEvent e, Player p);
    void onArmorTick(Player p);
}
