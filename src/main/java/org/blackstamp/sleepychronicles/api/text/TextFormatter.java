package org.blackstamp.sleepychronicles.api.text;

import net.kyori.adventure.text.format.TextColor;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class TextFormatter {
    private TextFormatter(){}

    public static Component toComponent(String value, @NotNull String color){
        net.minecraft.network.chat.TextColor textColor = net.minecraft.network.chat.TextColor
                .parseColor(color)
                .result()
                .orElse(net.minecraft.network.chat.TextColor.fromRgb(0xFFFFFF));

        return Component.literal(value)
                .withColor(textColor.getValue());
    }

    public static net.kyori.adventure.text.Component toKyoriComponent(String value, @NotNull String color) {
        TextColor textColor = TextColor.fromCSSHexString(color);

        if(textColor == null) textColor = TextColor.color(0xFFFFFF);

        return net.kyori.adventure.text.Component.text(value)
                .color(textColor);
    }
}
