package org.blackstamp.sleepychronicles.api.text;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import org.jetbrains.annotations.NotNull;

public class TextFormatter {

    public static String toIDString(String value){
        return value.toLowerCase().replaceAll("[^a-z0-9_]","_");
    }

    public static Component toComponent(String value, @NotNull String color){
        return Component.literal(value).withStyle(Style.EMPTY
                .withColor(TextColor.parseColor(color).getOrThrow()));
    }
}
