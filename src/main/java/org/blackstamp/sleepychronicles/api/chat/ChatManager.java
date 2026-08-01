package org.blackstamp.sleepychronicles.api.chat;

import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.blackstamp.sleepychronicles.api.color.BasicPalette;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.api.constant.ConstantFields;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatManager {

    private static final Sound BROADCAST_SOUND = Sound.sound(Key.key("block.note_block.pling"), Sound.Source.MASTER, 1.0F, 1.25F);
    private static final Sound COMMAND_SOUND = Sound.sound(Key.key("block.note_block.bell"), Sound.Source.MASTER, 1.0F, 0.85F);
    private static final Sound ERROR_SOUND = Sound.sound(Key.key("entity.enderman.teleport"), Sound.Source.MASTER, 1.0F, 0.5F);

    @Getter
    public enum ChatPrefix{
        SLEEPY(BasicPalette.DARK_GRAY.getColor() + "| " + SleepyPalette.SLEEPY.getColor1() + "[SleepyChronicles] " + BasicPalette.DARK_GRAY.getColor() + "» "),
        STAFF(BasicPalette.DARK_GRAY.getColor() + "| " + SleepyPalette.STAFF.getColor1() + "[Staff] " + BasicPalette.GOLD.getColor() + "» " + BasicPalette.GREEN.getColor());

        final String prefix;

        ChatPrefix(String prefix){ this.prefix = prefix; }
    }

    public static void sendMessage(Player p, boolean isError, String value){
        Sound sound = COMMAND_SOUND;
        String color = BasicPalette.GRAY.getColor();

        if(isError){
            color = BasicPalette.RED.getColor();
            sound = ERROR_SOUND;
        }

        p.sendMessage(ConstantFields.MINI_MESSAGE.deserialize(ChatPrefix.SLEEPY.getPrefix() + color + value));
        p.playSound(sound);
    }

    public static void sendWarning(Player p, String value, String color){
        p.sendActionBar(ConstantFields.MINI_MESSAGE.deserialize(color + value));
    }

    public static void sendBroadcast(String value){
        Bukkit.broadcast(ConstantFields.MINI_MESSAGE.deserialize(ChatPrefix.SLEEPY.getPrefix() + value));
        for(Player p : Bukkit.getOnlinePlayers())
            p.playSound(BROADCAST_SOUND);
    }

    public static void sendStaffMessage(Player p, String value){
        p.sendMessage(ConstantFields.MINI_MESSAGE.deserialize(ChatPrefix.STAFF.getPrefix() + value));
    }
}
