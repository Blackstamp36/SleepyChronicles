package org.blackstamp.sleepychronicles.api.text;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import org.jetbrains.annotations.NotNull;

public class TextFormatter {

    public static String toIDString(String value){
        return value.toLowerCase().replaceAll("[^a-z0-9_]","_");
    }

    public static Component toComponent(String value, @NotNull String color) {
        return Component.literal(value)
                .withStyle(Style.EMPTY
                        .withColor(TextColor.parseColor(color).getOrThrow()));
    }

    public static net.kyori.adventure.text.Component toKyoriComponent(String value, @NotNull String color) {
        return net.kyori.adventure.text.Component.text(value)
                .style(net.kyori.adventure.text.format.Style.empty()
                        .color(net.kyori.adventure.text.format.TextColor.fromHexString(color)));
    }
}
