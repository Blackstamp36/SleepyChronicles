package org.blackstamp.sleepychronicles.api.text;

import net.kyori.adventure.text.format.TextColor;
import net.minecraft.network.chat.Component;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.jetbrains.annotations.NotNull;

public class TextFormatter {
    private TextFormatter(){}

    // NMS helpers.
    public static Component toComponent(String value, @NotNull SleepyPalette palette){
        return toComponent(value,palette,0);
    }

    public static Component toComponent(String value, @NotNull SleepyPalette palette, int colorType){
        net.minecraft.network.chat.TextColor textColor = net.minecraft.network.chat.TextColor
                .parseColor(palette.getHex(colorType))
                .result()
                .orElse(net.minecraft.network.chat.TextColor.fromRgb(0xFFFFFF));

        return Component.literal(value)
                .withColor(textColor.getValue());
    }

    // Kyori helpers.
    public static net.kyori.adventure.text.Component toKyoriComponent(String value, @NotNull SleepyPalette palette){
        return toKyoriComponent(value,palette,0);
    }

    public static net.kyori.adventure.text.Component toKyoriComponent(String value, @NotNull SleepyPalette palette, int colorType) {
        TextColor textColor = TextColor.fromCSSHexString(palette.getHex(colorType));

        if(textColor == null) textColor = TextColor.color(0xFFFFFF);

        return net.kyori.adventure.text.Component.text(value)
                .color(textColor);
    }
}
