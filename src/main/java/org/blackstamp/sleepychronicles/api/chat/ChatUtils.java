package org.blackstamp.sleepychronicles.api.chat;

import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.blackstamp.sleepychronicles.api.constant.ConstantColors;
import org.blackstamp.sleepychronicles.api.constant.ConstantFields;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatUtils {

    private static final Sound BROADCAST_SOUND = Sound.sound(Key.key("block.note_block.pling"), Sound.Source.MASTER, 1.0F, 1.25F);

    @Getter
    public enum ChatPrefix{
        SLEEPY(ConstantColors.DARK_GRAY + "| " + ConstantColors.SLEEPY + "[SleepyChronicles] " + ConstantColors.DARK_GRAY + "» " + ConstantColors.GRAY),
        STAFF(ConstantColors.DARK_GRAY + "| " + ConstantColors.STAFF + "[Staff] " + ConstantColors.GOLD + "» " + ConstantColors.GREEN);

        final String prefix;

        ChatPrefix(String prefix){ this.prefix = prefix; }
    }

    public static void sendMessage(Player p, String value){
        p.sendMessage(ConstantFields.MINI_MESSAGE.deserialize(ChatPrefix.SLEEPY.getPrefix() + value));
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
