package org.blackstamp.sleepychronicles.api.chat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.api.text.TextFormatter;
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

    // Broadcast method.
    public static void sendBroadcast(String value){
        Component message = Component.text()
                .append(ChatPrefix.BROADCAST.getPrefix())
                .append(Component.text(value)).color(ChatPrefix.BROADCAST.getMessageColor())
                .build();

        Bukkit.broadcast(message);
    }

    // Title-related elements.
    public static void sendTitle(Player p, String value, SleepyPalette palette){ sendTitle(p,value,palette,0); }
    public static void sendTitle(Player p, String value, SleepyPalette palette, int type){
        p.showTitle(Title.title(TextFormatter.toKyoriComponent(value,palette,type), Component.empty()));
    }

    public static void sendSubtitle(Player p, String value, SleepyPalette palette){ sendSubtitle(p,value,palette,0); }
    public static void sendSubtitle(Player p, String value, SleepyPalette palette, int type){
        p.showTitle(Title.title(Component.empty(), TextFormatter.toKyoriComponent(value,palette,type)));
    }

    public static void sendActionBar(Player p, String value, SleepyPalette palette){ sendActionBar(p,value,palette,0); }
    public static void sendActionBar(Player p, String value, SleepyPalette palette, int type){
        p.sendActionBar(TextFormatter.toKyoriComponent(value,palette,type));
    }
}
