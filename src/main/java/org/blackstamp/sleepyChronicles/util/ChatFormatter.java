package org.blackstamp.sleepyChronicles.util;

import org.bukkit.ChatColor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public class ChatFormatter {

    /**
     * Function that translates the given String into another String with ChatColor format.
     * @param text to translate.
     * @return String containing the ChatColor.COLOR_CHAR color code character replaced by '&'.
     */
    public static String stringToString(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Function that translates the given String into a Component with ChatColor format.
     * @param text to translate.
     * @return Component containing the ChatColor.COLOR_CHAR color code character replaced by '&'.
     */
    public static String componentToString(TextComponent text) {
        return ChatColor.translateAlternateColorCodes('&', text.content());
    }

    /**
     * Function that translates the given Component into a String with ChatColor format.
     * @param text to translate.
     * @return String containing the ChatColor.COLOR_CHAR color code character replaced by '&'.
     */
    public static Component stringToComponent(String text) {
        return Component.text(ChatColor.translateAlternateColorCodes('&', text));
    }

    /**
     * Function that translates the given Component into another Component with ChatColor format.
     * @param text to translate.
     * @return Component containing the ChatColor.COLOR_CHAR color code character replaced by '&'.
     */
    public static Component componentToComponent(TextComponent text) {
        return Component.text(ChatColor.translateAlternateColorCodes('&', text.content()));
    }
    public static String format(String r) {
        return ChatColor.translateAlternateColorCodes('&', r);
    }
}

