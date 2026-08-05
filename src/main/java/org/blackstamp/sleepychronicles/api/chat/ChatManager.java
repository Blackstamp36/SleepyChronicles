package org.blackstamp.sleepychronicles.api.chat;

import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.blackstamp.sleepychronicles.api.color.BasicPalette;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.api.constant.ConstantFields;
import org.blackstamp.sleepychronicles.api.text.TextFormatter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatManager {

    // Color tags.
    private static final String DARK_GRAY_TAG = BasicPalette.DARK_GRAY.tag(true);
    private static final String RED_TAG = BasicPalette.RED.tag(true);
    private static final String GRAY_TAG = BasicPalette.GRAY.tag(true);
    private static final String GOLD_TAG = BasicPalette.GOLD.tag(true);
    private static final String GREEN_TAG = BasicPalette.GREEN.tag(true);

    // Prefixes.
    @Getter
    private enum ChatPrefix{
        SLEEPY(DARK_GRAY_TAG + "| " + SleepyPalette.SLEEPY.tag(true) + "[SleepyChronicles] " + DARK_GRAY_TAG + "» "),
        STAFF(DARK_GRAY_TAG + "| " + SleepyPalette.STAFF.tag(true) + "[Staff] " + GOLD_TAG + "» " + GREEN_TAG),
        NOTIFICATION(DARK_GRAY_TAG + "| " + SleepyPalette.NOTIFICATION.tag(true) + "[!] " + DARK_GRAY_TAG + "» ");

        private final String prefix;

        ChatPrefix(String prefix){ this.prefix = prefix; }
    }

    // Title elements.
    public static void sendTitle(Player p, String value, TextColor color){ // CHANGE THIS!! DON'T FORGET

        // p.showTitle(Title.title(title, Component.empty()));
    }

    public static void sendSubtitle(Player p, String value, String color){
        Component subtitle = TextFormatter.toKyoriComponent(value, color);

        p.showTitle(Title.title(Component.empty(), subtitle));
    }

    // Message elements.
    public static void sendMessage(Player p, boolean isError, String value){
        String color = isError ? RED_TAG : DARK_GRAY_TAG;
        String message = ChatPrefix.SLEEPY.getPrefix() + color + value;

        p.sendMessage(ConstantFields.MINI_MESSAGE.deserialize(message));
    }

    public static void sendNotification(Player p, String value){
        String message = ChatPrefix.SLEEPY.getPrefix() + GRAY_TAG + value;

        p.sendMessage(ConstantFields.MINI_MESSAGE.deserialize(message));
    }

    public static void sendWarning(Player p, String value, String color){
        String message = color + value;

        p.sendActionBar(ConstantFields.MINI_MESSAGE.deserialize(message));
    }

    public static void sendBroadcast(String value){
        String message = ChatPrefix.SLEEPY.getPrefix() + value;

        Bukkit.broadcast(ConstantFields.MINI_MESSAGE.deserialize(message));
    }

    public static void sendStaffMessage(Player p, String value){
        String message = ChatPrefix.STAFF.getPrefix() + value;

        p.sendMessage(ConstantFields.MINI_MESSAGE.deserialize(message));
    }
}
