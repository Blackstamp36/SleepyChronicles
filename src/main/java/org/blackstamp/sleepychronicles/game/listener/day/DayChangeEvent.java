package org.blackstamp.sleepychronicles.game.listener.day;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class DayChangeEvent extends Event {
    private final int day;

    public DayChangeEvent(int day){ this.day = day; }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public @NotNull HandlerList getHandlers(){ return handlers; }
    public static @NotNull HandlerList getHandlerList(){ return handlers; }
}
