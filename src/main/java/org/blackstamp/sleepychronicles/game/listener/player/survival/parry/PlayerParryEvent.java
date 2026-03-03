package org.blackstamp.sleepychronicles.game.listener.player.survival.parry;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Getter
public class PlayerParryEvent extends Event {
    private final Player player;
    private final ItemStack hand;

    public PlayerParryEvent(Player player, ItemStack hand){
        this.player = player;
        this.hand = hand;
    }

    private static HandlerList handlers = new HandlerList();

    @Override
    public @NotNull HandlerList getHandlers(){ return handlers; }
    public @NotNull static HandlerList getHandlerList(){ return handlers; }
}
