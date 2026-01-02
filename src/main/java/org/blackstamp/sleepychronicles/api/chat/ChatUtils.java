package org.blackstamp.sleepychronicles.api.chat;

import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChatUtils {

    @Getter
    public enum ChatColor{
        SLEEPY("<#6932a8>"),
        ANNOUNCEMENTS("<#f88a40>");

        final String hex;

        ChatColor(String hex){ this.hex = hex; }
    }

    @Getter
    public enum ChatPrefix{
        SLEEPY(ChatColor.SLEEPY.getHex() + "[SC] <dark_gray>» <gray>"),
        ANNOUNCEMENTS(ChatColor.ANNOUNCEMENTS.getHex() + "[🔔] <dark_gray>» <yellow>");

        final String prefix;

        ChatPrefix(String prefix){ this.prefix = prefix; }
    }

    public static void sendMessage(@NotNull Player p, String value){
        p.sendMessage(MiniMessage.miniMessage().deserialize(ChatPrefix.SLEEPY.getPrefix() + value));
    }

    public static void sendWarning(@NotNull Player p, String value, String color){
        p.sendMessage(MiniMessage.miniMessage().deserialize(color + value));
    }

    public static void sendBroadcast(String value){
        Bukkit.broadcast(MiniMessage.miniMessage().deserialize(ChatPrefix.ANNOUNCEMENTS.getPrefix() + value));
        for(Player p : Bukkit.getOnlinePlayers())
            p.playSound(Sound.sound(Key.key("block.note_block.pling"), Sound.Source.MASTER, 1.0F, 1.25F));
    }
}
