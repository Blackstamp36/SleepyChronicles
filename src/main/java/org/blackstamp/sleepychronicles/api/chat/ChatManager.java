package org.blackstamp.sleepychronicles.api.chat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatManager {
    private ChatManager(){}

    // Message elements.
    public static void sendMessage(Player p, String value){ sendMessage(p,value,ChatPrefix.SLEEPY); }
    public static void sendMessage(Player p, String value, ChatPrefix chatPrefix){
        Component message = Component.text()
                .append(chatPrefix.getPrefix())
                .append(Component.text(value)).color(chatPrefix.getMessageColor())
                .build();

        p.sendMessage(message);
    }

    // Title-related elements.
    public static void sendTitle(Player p, String value, TextColor color){
        Component title = Component.text(value).color(color);

        p.showTitle(Title.title(title, Component.empty()));
    }

    public static void sendSubtitle(Player p, String value, TextColor color){
        Component subtitle = Component.text(value).color(color);

        p.showTitle(Title.title(Component.empty(), subtitle));
    }

    public static void sendActionBar(Player p, String value, TextColor color){
        p.sendActionBar(Component.text(value).color(color));
    }

    // Broadcast.
    public static void sendBroadcast(String value){
        Component message = Component.text()
                .append(ChatPrefix.BROADCAST.getPrefix())
                .append(Component.text(value)).color(ChatPrefix.BROADCAST.getMessageColor())
                .build();

        Bukkit.broadcast(message);
    }
}
