package org.blackstamp.sleepychronicles.api.chat;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;

// Chat prefixes.
public enum ChatPrefix{

    SLEEPY(Component.text()
            .append(Component.text("| ").color(NamedTextColor.DARK_GRAY))
            .append(Component.text("[SleepyChronicles]").color(TextColor.fromCSSHexString(SleepyPalette.SLEEPY.getHex())))
            .append(Component.text("» ").color(NamedTextColor.DARK_GRAY))
            .build(),
                    NamedTextColor.GRAY),

    STAFF(Component.text()
            .append(Component.text("| ").color(NamedTextColor.DARK_GRAY))
            .append(Component.text("[Staff]").color(TextColor.fromCSSHexString(SleepyPalette.STAFF.getHex())))
            .append(Component.text("» ")).color(NamedTextColor.GOLD)
            .build(),
                    NamedTextColor.GRAY),

    ERROR(Component.text()
            .append(Component.text("| ").color(NamedTextColor.DARK_GRAY))
            .append(Component.text("[Error]").color(TextColor.fromCSSHexString(SleepyPalette.ERROR.getHex())))
            .append(Component.text("» ")).color(NamedTextColor.RED)
            .build(),
                    NamedTextColor.GRAY),

    BROADCAST(Component.text()
            .append(Component.text("| ").color(NamedTextColor.DARK_GRAY))
            .append(Component.text("[!]").color(TextColor.fromCSSHexString(SleepyPalette.BROADCAST.getHex())))
            .append(Component.text("» ")).color(NamedTextColor.DARK_GRAY)
            .build()
            ,NamedTextColor.GRAY);

    @Getter private final Component prefix;
    @Getter private final TextColor messageColor;

    ChatPrefix(Component prefix, TextColor messageColor){
        this.prefix = prefix;
        this.messageColor = messageColor;
    }
}
